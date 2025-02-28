package chat.adapter.output;

import chat.adapter.output.memory.SseConnectionMemoryRepository;
import chat.application.port.output.SseConnectionPoolPort;
import chat.application.sse.UserSseConnection;
import global.annotation.output.PersistenceAdapter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class SseConnectionPoolAdapter implements SseConnectionPoolPort<String, UserSseConnection> {

    private final SseConnectionMemoryRepository sseConnectionMemoryRepository;

    @Override
    public void addSession(String userId, UserSseConnection connection) {
        sseConnectionMemoryRepository.put(userId, connection);
    }

    @Override
    public UserSseConnection getSession(String userId) {
        return sseConnectionMemoryRepository.get(userId);
    }

    @Override
    public void removeSession(UserSseConnection session) {
        sseConnectionMemoryRepository.remove(session.getUserId());
    }

}

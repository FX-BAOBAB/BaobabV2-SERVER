package chat.adapter.output;

import chat.adapter.output.memory.SseConnectionMemoryRepository;
import chat.application.port.output.SseConnectionRegistryPort;
import chat.application.sse.UserSseConnection;
import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class SseConnectionRegistryAdapter implements
    SseConnectionRegistryPort<String, UserSseConnection> {

    private final SseConnectionMemoryRepository sseConnectionMemoryRepository;

    @Override
    public void saveEmitter(String userId, UserSseConnection connection) {
        sseConnectionMemoryRepository.put(userId, connection);
    }

    @Override
    public UserSseConnection findEmitter(String userId) {
        return sseConnectionMemoryRepository.get(userId);
    }

    @Override
    public void deleteEmitter(UserSseConnection session) {
        sseConnectionMemoryRepository.remove(session.getUserId());
    }

}

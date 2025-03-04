package chat.application.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SseConnectionStoreImpl implements SseConnectionStore<String, UserSseConnection> {

    private static final Map<String, UserSseConnection> connectionStore = new ConcurrentHashMap<>();

    @Override
    public void saveEmitter(String userId, UserSseConnection connection) {
        connectionStore.put(userId, connection);
    }

    @Override
    public UserSseConnection findEmitter(String userId) {
        return connectionStore.get(userId);
    }

    @Override
    public void deleteEmitter(UserSseConnection connection) {
        connectionStore.remove(connection.getUserId());
    }

}

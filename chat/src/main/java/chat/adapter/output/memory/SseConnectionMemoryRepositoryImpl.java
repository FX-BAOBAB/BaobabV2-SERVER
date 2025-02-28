package chat.adapter.output.memory;

import chat.application.sse.UserSseConnection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class SseConnectionMemoryRepositoryImpl implements SseConnectionMemoryRepository {

    private static final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    @Override
    public void put(String userId, UserSseConnection connection) {
        connectionPool.put(userId, connection);
    }

    @Override
    public UserSseConnection get(String userId) {
        return connectionPool.get(userId);
    }

    @Override
    public void remove(String userId) {
        connectionPool.remove(userId);
    }

}

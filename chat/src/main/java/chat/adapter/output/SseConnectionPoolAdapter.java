package chat.adapter.output;

import chat.application.port.output.SseConnectionPoolPort;
import chat.application.sse.UserSseConnection;
import global.annotation.output.PersistenceAdapter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@PersistenceAdapter
public class SseConnectionPoolAdapter implements SseConnectionPoolPort<String, UserSseConnection> {

    private static final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    @Override
    public void addSession(String userId, UserSseConnection connection) {
        connectionPool.put(userId, connection);
    }

    @Override
    public UserSseConnection getSession(String userId) {
        return connectionPool.get(userId);
    }

    @Override
    public void removeSession(UserSseConnection session) {
        connectionPool.remove(session.getUserId());
    }

}

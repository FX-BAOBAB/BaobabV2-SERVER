package chat.application.sse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SseConnectionStoreImpl implements SseConnectionStore<String, UserSseConnection> {

    private static final Map<String, UserSseConnection> connectionStore = new ConcurrentHashMap<>();

    @Override
    public UserSseConnection saveEmitter(String userId, UserSseConnection connection) {
        return connectionStore.put(userId, connection);
    }

    @Override
    public List<UserSseConnection> findEmitter(List<String> userIdList) {
        return userIdList.stream()
            .map(connectionStore::get)
            .filter(connection -> connection != null)
            .toList();
    }

    @Override
    public void deleteEmitter(UserSseConnection connection) {
        connectionStore.remove(connection.getUserId());
    }

}

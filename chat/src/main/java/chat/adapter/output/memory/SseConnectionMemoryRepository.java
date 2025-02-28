package chat.adapter.output.memory;

import chat.application.sse.UserSseConnection;

public interface SseConnectionMemoryRepository {

    void put(String userId, UserSseConnection connection);

    UserSseConnection get(String userId);

    void remove(String userId);

}

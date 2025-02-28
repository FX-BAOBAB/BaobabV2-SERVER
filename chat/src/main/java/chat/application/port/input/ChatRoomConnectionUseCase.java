package chat.application.port.input;

import chat.application.sse.UserSseConnection;

public interface ChatRoomConnectionUseCase {

    UserSseConnection connectChatRoom(String userId);

    void disconnectChatRoom(String userId, UserSseConnection connection);

}

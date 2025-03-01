package chat.application.port.input;

import chat.application.sse.UserSseConnection;

public interface ChatRoomConnectionUseCase {

    void connectChatRoom(String userId);

    void disconnectChatRoom(String userId, UserSseConnection connection);

}

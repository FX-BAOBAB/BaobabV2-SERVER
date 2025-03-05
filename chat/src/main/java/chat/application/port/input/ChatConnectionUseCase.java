package chat.application.port.input;

import chat.application.sse.UserSseConnection;

public interface ChatConnectionUseCase {

    void connectChatRoom(String userId, String chatRoomId);

    void disconnectChatRoom(String userId, String chatRoomId, UserSseConnection connection);

}

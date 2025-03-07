package chat.application.port.input;

import chat.application.sse.UserSseConnection;
import java.util.List;

public interface ChatConnectionUseCase {

    UserSseConnection connectChatRoom(String userId);

    void disconnectChatRoom(String userId, UserSseConnection connection);

    List<UserSseConnection> getConnectedUserSseList(List<String> userId);

    String getConnectedServerAddress(String userId);

}

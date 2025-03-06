package chat.application.port.input;

import java.util.List;

public interface UserChatReaderUseCase {

    List<String> getUserChatsExcludingSender(String chatRoomId, String senderId);

}
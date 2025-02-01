package chat.application.port.input;

import chat.domain.command.ChatRoomSaveCommand;

public interface ChatRoomSaveUseCase {

    Boolean saveChatRoom(ChatRoomSaveCommand chatRoomSaveCommand);

}

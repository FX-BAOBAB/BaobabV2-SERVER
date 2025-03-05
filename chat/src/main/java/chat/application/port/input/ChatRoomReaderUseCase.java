package chat.application.port.input;

import chat.domain.command.ChatRoomReaderCommand;

public interface ChatRoomReaderUseCase {

    String getChatRoom(ChatRoomReaderCommand command);

}
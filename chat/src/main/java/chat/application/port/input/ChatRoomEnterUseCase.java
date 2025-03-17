package chat.application.port.input;

import chat.adapter.input.web.response.ChatRoomResponse;
import chat.domain.command.ChatRoomReaderCommand;

public interface ChatRoomEnterUseCase {

    ChatRoomResponse enterChatRoom(ChatRoomReaderCommand command);

    interface ChatMessageReaderUseCase {

    }
}
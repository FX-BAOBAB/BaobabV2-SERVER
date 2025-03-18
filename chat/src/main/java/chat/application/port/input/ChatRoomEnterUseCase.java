package chat.application.port.input;

import chat.adapter.input.web.response.ChatRoomEnterResponse;
import chat.domain.command.ChatRoomReaderCommand;

public interface ChatRoomEnterUseCase {

    ChatRoomEnterResponse enterChatRoom(ChatRoomReaderCommand command);

    interface ChatMessageReaderUseCase {

    }
}
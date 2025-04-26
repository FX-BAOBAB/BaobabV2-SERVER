package chat.application.port.input;

import chat.adapter.input.web.response.ChatRoomEnterResponse;
import chat.domain.command.ChatRoomReaderCommand;

public interface ChatRoomEnterUseCase {

    ChatRoomEnterResponse enterChatRoomByArticleId(ChatRoomReaderCommand command);

    ChatRoomEnterResponse enterChatRoomByChatRoomId(String userId, String chatRoomId);

}
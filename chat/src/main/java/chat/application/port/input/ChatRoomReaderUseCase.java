package chat.application.port.input;

import chat.adapter.input.web.response.ChatRoomResponse;
import chat.domain.command.ChatRoomSearchCommand;
import java.util.List;

public interface ChatRoomReaderUseCase {

    List<ChatRoomResponse> getChatRooms(ChatRoomSearchCommand chatRoomSearchCommand);

}

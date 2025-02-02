package chat.application.port.input;

import chat.adapter.output.persistence.repository.ChatRoomDocument;
import java.util.List;

public interface ChatRoomReaderUseCase {

    List<ChatRoomDocument> getChatRoomList(String userId);

}

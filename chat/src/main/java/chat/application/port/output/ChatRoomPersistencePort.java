package chat.application.port.output;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.domain.dto.ChatRoomSaveForm;
import java.util.List;
import java.util.Optional;

public interface ChatRoomPersistencePort {

    String saveChatRoom(ChatRoomSaveForm chatRoomSaveForm);

    List<ChatRoomDocument> getChatRoomListBy(String articleId);

}

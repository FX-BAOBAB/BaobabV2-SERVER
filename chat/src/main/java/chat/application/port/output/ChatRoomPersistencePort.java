package chat.application.port.output;

import chat.domain.dto.ChatRoomSaveForm;
import java.util.Optional;

public interface ChatRoomPersistencePort {

    boolean existsChatRoomBy(String chatRoomId);

    String saveChatRoom(ChatRoomSaveForm chatRoomSaveForm);

    Optional<String> getChatRoomBy(String articleId);

}

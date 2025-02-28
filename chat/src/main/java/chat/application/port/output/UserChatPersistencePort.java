package chat.application.port.output;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.domain.dto.UserChatSaveForm;
import java.util.Optional;

public interface UserChatPersistencePort {

    void saveUserChat(UserChatSaveForm userChatSaveForm);

    Boolean existsBy(String chatRoomId, String userId);

    Optional<UserChatDocument> getUserChat(String chatRoomId, String userId);

}

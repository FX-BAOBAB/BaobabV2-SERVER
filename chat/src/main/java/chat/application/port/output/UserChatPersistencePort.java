package chat.application.port.output;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.domain.dto.UserChatSaveForm;
import chat.domain.dto.UserChatUpdateForm;
import java.util.List;
import java.util.Optional;

public interface UserChatPersistencePort {

    void saveUserChat(UserChatSaveForm userChatSaveForm);

    Boolean existsBy(String chatRoomId, String userId);

    Optional<UserChatDocument> getUserChat(String chatRoomId, String userId);

    List<UserChatDocument> getUserChatsExcludingSender(String chatRoomId, String senderId);

    List<UserChatDocument> getUserChatList(String chatRoomId, List<String> userIdList);

    void updateUserChat(List<UserChatUpdateForm> userChatUpdateFormList);

}

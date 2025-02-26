package chat.application.port.output;

import chat.domain.dto.UserChatSaveForm;

public interface UserChatPersistencePort {

    Boolean saveUserChat(UserChatSaveForm userChatSaveForm);

    Boolean existsBy(String chatRoomId, String userId);
}

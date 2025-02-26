package chat.application;

import chat.application.port.input.UserChatSaveUseCase;
import chat.application.port.output.UserChatPersistencePort;
import chat.domain.dto.UserChatSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatSaveService implements UserChatSaveUseCase {

    private final UserChatPersistencePort userChatPersistencePort;

    @Override
    public void saveUserChatIfNotExists(String chatRoomId, String userId) {
        Boolean existsUserChat = userChatPersistencePort.existsBy(chatRoomId, userId);
        if (!existsUserChat) {
            userChatPersistencePort.saveUserChat(UserChatSaveForm.of(chatRoomId, userId));
        }
    }

}

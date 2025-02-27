package chat.application;

import chat.application.port.input.UserChatSaveUseCase;
import chat.application.port.output.UserChatPersistencePort;
import chat.domain.dto.UserChatSaveForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatSaveService implements UserChatSaveUseCase {

    private final UserChatPersistencePort userChatPersistencePort;

    @Override
    public void saveUserChatIfNotExists(List<UserChatSaveForm> formList) {
        for (UserChatSaveForm form : formList) {
            Boolean existsUserChat = userChatPersistencePort.existsBy(form.getChatRoomId(), form.getUserId());
            if (!existsUserChat) {
                userChatPersistencePort.saveUserChat(form);
            }
        }
    }

}

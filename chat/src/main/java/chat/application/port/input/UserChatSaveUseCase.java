package chat.application.port.input;

import chat.domain.dto.UserChatSaveForm;
import java.util.List;

public interface UserChatSaveUseCase {

    void saveUserChatIfNotExists(List<UserChatSaveForm> userChatSaveFormList);

}

package chat.application;

import chat.application.port.output.UserChatPersistencePort;
import chat.domain.dto.UserChatSaveForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatSaveService {

    private final UserChatPersistencePort userChatPersistencePort;

    public void saveUserChatIfNotExists(List<UserChatSaveForm> formList) {
        formList.stream()
            .filter(form -> !userChatPersistencePort.existsBy(form.getChatRoomId(), form.getUserId()))
            .forEach(userChatPersistencePort::saveUserChat);
    }

}

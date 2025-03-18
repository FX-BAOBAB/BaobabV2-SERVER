package chat.application;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.application.port.output.UserChatPersistencePort;
import chat.domain.dto.UserChatUpdateForm;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatUpdateService {

    private final UserChatPersistencePort userChatPersistencePort;

    public void updateLastChatAt(String chatRoomId, List<String> userIdList, LocalDateTime lastChatAt) {

        List<UserChatDocument> userChatList = userChatPersistencePort.getUserChatList(chatRoomId,
            userIdList);

        for (UserChatDocument userChatDocument : userChatList) {
            userChatDocument.setLastChatAt(lastChatAt);
        }

        userChatPersistencePort.updateUserChat(UserChatUpdateForm.of(userChatList));

    }

}

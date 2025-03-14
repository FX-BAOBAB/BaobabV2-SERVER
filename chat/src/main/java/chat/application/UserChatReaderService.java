package chat.application;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.application.port.input.UserChatReaderUseCase;
import chat.application.port.output.UserChatPersistencePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatReaderService implements UserChatReaderUseCase {

    private final UserChatPersistencePort userChatPersistencePort;

    @Override
    public List<String> getUserChatsExcludingSender(String chatRoomId, String senderId) {
        return userChatPersistencePort.getUserChatsExcludingSender(chatRoomId, senderId).stream()
            .map(UserChatDocument::getUserId)
            .toList();
    }

}

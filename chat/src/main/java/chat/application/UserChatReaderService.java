package chat.application;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.application.port.output.UserChatPersistencePort;
import chat.domain.command.ChatRoomSearchCommand;
import chat.domain.dto.ChatRoomSearchForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatReaderService {

    private final UserChatPersistencePort userChatPersistencePort;

    public List<String> getUserChatsExcludingSender(String chatRoomId, String senderId) {
        return userChatPersistencePort.getUserChatsExcludingSender(chatRoomId, senderId).stream()
            .map(UserChatDocument::getUserId)
            .toList();
    }

    public List<UserChatDocument> getUserChat(ChatRoomSearchCommand command) {
        return userChatPersistencePort.getUserChats(ChatRoomSearchForm.of(command));
    }

}

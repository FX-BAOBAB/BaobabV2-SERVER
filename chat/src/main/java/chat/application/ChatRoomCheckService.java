package chat.application;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.application.port.output.UserChatPersistencePort;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomCheckService {

    private final UserChatPersistencePort userChatPersistencePort;

    public Optional<String> existsChatRoomBy(List<String> chatRoomIdList, String userId) {
        return chatRoomIdList.stream()
            .filter(chatRoomId -> userChatPersistencePort.getUserChat(chatRoomId, userId).isPresent())
            .findFirst();
    }

    public Optional<String> existsChatRoomBy(String chatRoomId, String userId) {
        return userChatPersistencePort.getUserChat(chatRoomId, userId)
            .map(userChat -> chatRoomId);
    }

}

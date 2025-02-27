package chat.application;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.application.port.input.ChatRoomCheckUseCase;
import chat.application.port.output.UserChatPersistencePort;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomCheckService implements ChatRoomCheckUseCase {

    private final UserChatPersistencePort userChatPersistencePort;

    @Override
    public Optional<String> existsChatRoomBy(List<String> chatRoomIdList, String userId) {
        for (String chatRoomId : chatRoomIdList) {
            if (userChatPersistencePort.getUserChat(chatRoomId, userId).isPresent()) {
                return Optional.ofNullable(chatRoomId);
            }
        }
        return Optional.empty();
    }

}

package chat.application;

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
        return chatRoomIdList.stream()
            .filter(chatRoomId -> userChatPersistencePort.getUserChat(chatRoomId, userId).isPresent())
            .findFirst();
    }

}

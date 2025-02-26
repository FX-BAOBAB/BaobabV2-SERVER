package chat.application;

import chat.application.port.input.ChatRoomCheckUseCase;
import chat.application.port.output.ChatRoomPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomCheckService implements ChatRoomCheckUseCase {

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    @Override
    public boolean existsChatRoomBy(String chatRoomId) {
        return chatRoomPersistencePort.existsChatRoomBy(chatRoomId);
    }

}

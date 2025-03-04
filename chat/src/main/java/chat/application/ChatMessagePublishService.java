package chat.application;

import chat.application.port.input.ChatMessageSaveUseCase;
import chat.application.port.input.ChatRoomCheckUseCase;
import chat.domain.command.ChatMessagePublishCommand;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessagePublishService {

    private final ChatMessageSaveUseCase chatMessageSaveUseCase;
    private final ChatRoomCheckUseCase chatRoomCheckUseCase;

    public boolean publishMessage(ChatMessagePublishCommand command) {

        Optional<String> chatRoomId = chatRoomCheckUseCase.existsChatRoomBy(List.of(command.getChatRoomId()),
            command.getUserId());

        if (chatRoomId.isEmpty()) {
            throw new RuntimeException(); // TODO 예외처리
        }

        chatMessageSaveUseCase.saveChatMessage(command);

        // Kafka

        return true;
    }

}

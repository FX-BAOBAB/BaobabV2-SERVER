package chat.application;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.ChatMessagePublishUseCase;
import chat.application.port.input.ChatMessageSaveUseCase;
import chat.application.port.input.ChatRoomCheckUseCase;
import chat.application.port.input.MessageProducerUseCase;
import chat.application.port.output.KafkaProducerPort;
import chat.domain.ChatMessage;
import chat.domain.command.ChatMessagePublishCommand;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessagePublishService implements ChatMessagePublishUseCase {

    private final ChatMessageSaveUseCase chatMessageSaveUseCase;
    private final ChatRoomCheckUseCase chatRoomCheckUseCase;
    private final MessageProducerUseCase messageProducerUseCase;

    @Override
    public boolean publishMessage(ChatMessagePublishCommand command) {
        Optional<String> chatRoomId = chatRoomCheckUseCase.existsChatRoomBy(List.of(command.getChatRoomId()),
            command.getUserId());

        if (chatRoomId.isEmpty()) {
            throw new RuntimeException(); // TODO 예외처리
        }

        MessageDocument messageDocument = chatMessageSaveUseCase.saveChatMessage(command);

        // Kafka
        messageProducerUseCase.produceMessage(ChatMessage.of(messageDocument, command.getUserId()));
        return true;
    }

}

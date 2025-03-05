package chat.application;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.ChatMessageSaveUseCase;
import chat.application.port.input.ChatRoomCheckUseCase;
import chat.application.port.input.MessageProducerUseCase;
import chat.application.port.output.KafkaProducerPort;
import chat.domain.ChatMessage;
import chat.domain.command.ChatMessageCommand;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducerService implements MessageProducerUseCase {

    private final ChatMessageSaveUseCase chatMessageSaveUseCase;
    private final ChatRoomCheckUseCase chatRoomCheckUseCase;

    private final KafkaProducerPort kafkaProducerPort;

    private static final String TOPIC_NAME = "chatMessage";

    @Override
    public boolean produceMessage(ChatMessageCommand command) {

        Optional<String> chatRoomId = chatRoomCheckUseCase.existsChatRoomBy(List.of(command.getChatRoomId()),
            command.getUserId());

        if (chatRoomId.isEmpty()) {
            throw new RuntimeException(); // TODO 예외처리
        }

        MessageDocument messageDocument = chatMessageSaveUseCase.saveChatMessage(command);
        kafkaProducerPort.send(TOPIC_NAME, ChatMessage.of(messageDocument, command.getUserId()));

        return true;
    }

}

package chat.application;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.ChatRoomCheckUseCase;
import chat.application.port.input.MessageProducerUseCase;
import chat.application.port.input.UserChatReaderUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.application.port.output.KafkaProducerPort;
import chat.domain.ChatMessage;
import chat.domain.command.ChatMessageCommand;
import chat.domain.dto.ChatMessageSaveForm;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerService implements MessageProducerUseCase {

    private final ChatRoomCheckUseCase chatRoomCheckUseCase;
    private final UserChatReaderUseCase userChatReaderUseCase;

    private final KafkaProducerPort kafkaProducerPort;
    private final ChatMessagePersistencePort chatMessagePersistencePort;

    private static final String TOPIC_NAME = "chatMessage";

    @Override
    public boolean produceMessage(ChatMessageCommand command) {

        Optional<String> chatRoomId = chatRoomCheckUseCase.existsChatRoomBy(
            List.of(command.getChatRoomId()), command.getUserId());

        if (chatRoomId.isEmpty()) {
            throw new RuntimeException(); // TODO 예외처리
        }

        MessageDocument savedMessage = chatMessagePersistencePort.saveMessage(
            ChatMessageSaveForm.of(command));

        // receiverID List 조회
        List<String> receiverIdList = userChatReaderUseCase.getUserChatsExcludingSender(
            savedMessage.getChatRoomId(), command.getUserId());

        kafkaProducerPort.send(TOPIC_NAME,
            ChatMessage.of(savedMessage, command.getUserId(), receiverIdList));

        return true;
    }

}

package chat.application;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.MessageProducerUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.application.port.output.KafkaProducerPort;
import chat.domain.ChatMessage;
import chat.domain.command.ChatMessageCommand;
import chat.domain.dto.ChatMessageSaveForm;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerService implements MessageProducerUseCase {

    private final ChatRoomCheckService chatRoomCheckService;
    private final UserChatReaderService userChatReaderService;

    private final KafkaProducerPort kafkaProducerPort;
    private final ChatMessagePersistencePort chatMessagePersistencePort;

    private static final String TOPIC_NAME = "chatMessage";
    private final UserChatUpdateService userChatUpdateService;

    @Override
    public boolean produceMessage(ChatMessageCommand command) {

        Optional<String> chatRoomId = chatRoomCheckService.existsChatRoomBy(
            List.of(command.getChatRoomId()), command.getUserId());

        if (chatRoomId.isEmpty()) {
            throw new RuntimeException(); // TODO 예외처리
        }

        MessageDocument savedMessage = chatMessagePersistencePort.saveMessage(
            ChatMessageSaveForm.of(command));

        // receiverID List 조회
        List<String> receiverIdList = userChatReaderService.getUserChatsExcludingSender(
            savedMessage.getChatRoomId(), command.getUserId());

        kafkaProducerPort.send(TOPIC_NAME,
            ChatMessage.of(savedMessage, command.getUserId(), receiverIdList));



        // receiverIdList 에 sender 추가
        List<String> userIdList = new ArrayList<>(receiverIdList);
        userIdList.add(command.getUserId());

        // 마지막 채팅방 날짜 업데이트
        userChatUpdateService.updateLastChatAt(
            chatRoomId.get(), userIdList, savedMessage.getSentAt()
        );

        return true;
    }

}

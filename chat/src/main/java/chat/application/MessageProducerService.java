package chat.application;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.MessageProducerUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.application.port.output.KafkaProducerPort;
import chat.core.common.error.ChatErrorCode;
import chat.core.common.exception.chatroom.ChatRoomNotFoundException;
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

        String chatRoomId = chatRoomCheckService.existsChatRoomBy(List.of(command.getChatRoomId()),
                command.getUserId())
            .orElseThrow(() -> new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));

        MessageDocument savedMessage = chatMessagePersistencePort.saveMessage(
            ChatMessageSaveForm.of(command));

        // receiverID List 조회
        List<String> receiverIdList = userChatReaderService.getUserChatsExcludingSender(
            savedMessage.getChatRoomId(), command.getUserId());

        log.info("수신 대상 ID : {}", receiverIdList);

        kafkaProducerPort.send(TOPIC_NAME,
            ChatMessage.of(savedMessage, command.getUserId(), receiverIdList));

        // receiverIdList 에 sender 추가
        List<String> userIdList = new ArrayList<>(receiverIdList);
        userIdList.add(command.getUserId());

        log.info("채팅방 날짜 업데이트");
        // 마지막 채팅방 날짜 업데이트
        userChatUpdateService.updateLastChatAt(
            chatRoomId, userIdList, savedMessage.getSentAt()
        );

        return true;
    }

}

package chat.application;

import chat.adapter.input.web.response.ChatMessageResponse;
import chat.adapter.output.client.UserClient;
import chat.application.port.input.ChatMessageReaderUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.core.common.error.ChatErrorCode;
import chat.core.common.exception.chatroom.ChatRoomNotFoundException;
import chat.domain.command.ChatMessageSearchCommand;
import chat.domain.dto.ChatMessageSearchForm;
import chat.domain.dto.UserSimpleInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageReaderService implements ChatMessageReaderUseCase {

    private final ChatMessagePersistencePort chatMessagePersistencePort;
    private final ChatRoomCheckService chatRoomCheckService;
    private final UserClient userClient;

    @Override
    public List<ChatMessageResponse> getChatMessages(ChatMessageSearchCommand command) {

        // 채팅방 존재유무 확인
        chatRoomCheckService.existsChatRoomBy(List.of(command.getChatRoomId()), command.getUserId())
            .orElseThrow(() -> new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));

        // 조회 요청 userId 와 DB 에서 조회한 메시지 전송 userId 비교
        return chatMessagePersistencePort.getMessages(ChatMessageSearchForm.of(command)).stream()
            .map(messageDocument -> {
                boolean isMine = command.getUserId().equals(messageDocument.getUserId());
                UserSimpleInfo userSimpleInfo = userClient.getUserSimpleInfo(
                    messageDocument.getUserId());
                return ChatMessageResponse.of(messageDocument, userSimpleInfo, isMine);
            })
            .toList();
    }

}

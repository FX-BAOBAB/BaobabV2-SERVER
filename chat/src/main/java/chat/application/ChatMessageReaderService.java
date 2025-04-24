package chat.application;

import chat.adapter.input.web.response.ChatMessageResponse;
import chat.adapter.output.client.UserClient;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.ChatMessageReaderUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.core.common.error.ChatErrorCode;
import chat.core.common.exception.chatroom.ChatRoomNotFoundException;
import chat.domain.command.ChatMessageSearchCommand;
import chat.domain.dto.ChatMessageSearchForm;
import chat.domain.dto.UserSimpleInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class ChatMessageReaderService implements ChatMessageReaderUseCase {

    private final ChatMessagePersistencePort chatMessagePersistencePort;
    private final ChatRoomCheckService chatRoomCheckService;
    private final UserClient userClient;


    @Override
    public List<ChatMessageResponse> getChatMessages(ChatMessageSearchCommand command) {

        // 1. 채팅방 존재유무 확인
        chatRoomCheckService.existsChatRoomBy(List.of(command.getChatRoomId()), command.getUserId())
            .orElseThrow(() -> new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));

        // 2. 채팅 메시지 조회
        List<MessageDocument> messages = chatMessagePersistencePort.getMessages(
            ChatMessageSearchForm.of(command));

        // 3. 메시지가 없는 경우 빈 리스트 반환
        if (CollectionUtils.isEmpty(messages)) {
            return List.of();
        }

        // 4. 사용자 정보 조회
        Map<String, UserSimpleInfo> userSimpleInfoMap = getUserInfoMapByUserId(messages);

        // 5. 메시지 반환
        return messages.stream()
            .map(message -> {
                UserSimpleInfo userSimpleInfo = userSimpleInfoMap.get(message.getUserId());
                boolean isMine = command.getUserId().equals(message.getUserId());
                return ChatMessageResponse.of(message, userSimpleInfo, isMine);
            })
            .toList()
            .reversed();
    }

    private Map<String, UserSimpleInfo> getUserInfoMapByUserId(List<MessageDocument> messages) {
        Set<String> uniqueUserIds = messages.stream()
            .map(MessageDocument::getUserId)
            .collect(Collectors.toSet());

        Map<String, UserSimpleInfo> userSimpleInfoMap = new HashMap<>();
        for (String userId : uniqueUserIds) {
            UserSimpleInfo userSimpleInfo = userClient.getUserSimpleInfo(userId);
            userSimpleInfoMap.put(userId, userSimpleInfo);
        }
        return userSimpleInfoMap;
    }

}

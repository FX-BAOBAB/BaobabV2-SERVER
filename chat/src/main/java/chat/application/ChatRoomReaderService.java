package chat.application;

import chat.adapter.input.web.response.ChatRoomResponse;
import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.application.port.input.ChatRoomReaderUseCase;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.domain.command.ChatRoomSearchCommand;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomReaderService implements ChatRoomReaderUseCase {

    private final UserChatReaderService userChatReaderService;
    private final ChatRoomPersistencePort chatRoomPersistencePort;

    public List<ChatRoomResponse> getChatRooms(ChatRoomSearchCommand command) {
        List<UserChatDocument> userChatDocumentList = userChatReaderService.getUserChat(command);

        List<String> chatRoomIdList = userChatDocumentList.stream()
            .map(UserChatDocument::getChatRoomId)
            .toList();

        List<ChatRoomDocument> chatRoomDocumentList = chatRoomPersistencePort.getChatRoomList(
            chatRoomIdList);

        Map<String, LocalDateTime> lastChatAtMap = userChatDocumentList.stream()
            .collect(Collectors.toMap(UserChatDocument::getChatRoomId, UserChatDocument::getLastChatAt));

        return chatRoomDocumentList.stream()
            .map(chatRoomDocument -> ChatRoomResponse.builder()
                .chatRoomId(chatRoomDocument.getId())
                .title(chatRoomDocument.getTitle())
                .articleId(chatRoomDocument.getArticleId())
                .thumbnailUrl(chatRoomDocument.getThumbnailUrl())
                .lastChatAt(lastChatAtMap.get(chatRoomDocument.getId()))
                .build())
            .toList();
    }

}

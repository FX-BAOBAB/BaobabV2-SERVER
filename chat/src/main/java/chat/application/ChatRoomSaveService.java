package chat.application;

import chat.adapter.output.client.ArticleClient;
import chat.adapter.output.client.UserClient;
import chat.adapter.output.client.dto.ArticleFeignInfo;
import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.application.port.input.ChatRoomCheckUseCase;
import chat.application.port.input.UserChatSaveUseCase;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.domain.command.ChatRoomSaveCommand;
import chat.domain.dto.ChatRoomSaveForm;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomSaveService {

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    private final ChatRoomCheckUseCase chatRoomCheckUseCase;

    private final UserChatSaveUseCase userChatSaveUseCase;

    private final ChatRoomGenerator chatRoomGenerator;

    private final ArticleClient articleClient;

    private final UserClient userClient;

    @Transactional
    public String createChatRoom(ChatRoomSaveCommand chatRoomSaveCommand) {

        // 1. articleId 로 chatRoomIds 조회
        List<String> chatRoomIdList = getChatRoomIdList(chatRoomSaveCommand);

        // 2. chatRoomIds 와 buyerId 로 chatRoom 조회
        Optional<String> chatRoomId = chatRoomCheckUseCase.existsChatRoomBy(chatRoomIdList,
            chatRoomSaveCommand.getBuyerId());

        // 3-1. 존재하면 id 반환
        if (chatRoomId.isPresent()) {
            return chatRoomId.get();
        }

        // 3.2. ChatRoom 생성
        ArticleFeignInfo articleInfo = articleClient.getArticleBy(
            chatRoomSaveCommand.getArticleId());

        String buyerNickName = userClient.getNickname(chatRoomSaveCommand.getBuyerId());
        String sellerNickName = userClient.getNickname(articleInfo.getUserId());

        String defaultTitle = chatRoomGenerator.generateDefaultTitle(
            List.of(buyerNickName, sellerNickName));

        String newChatRoomId = chatRoomPersistencePort.saveChatRoom(
            ChatRoomSaveForm.of(defaultTitle, chatRoomSaveCommand.getArticleId(),
                articleInfo.getArticleImage()));

        // 4. UserChat 생성
        userChatSaveUseCase.saveUserChatIfNotExists(newChatRoomId, chatRoomSaveCommand.getBuyerId());
        userChatSaveUseCase.saveUserChatIfNotExists(newChatRoomId, articleInfo.getUserId());

        return newChatRoomId;

    }

    private List<String> getChatRoomIdList(ChatRoomSaveCommand chatRoomSaveCommand) {
        return chatRoomPersistencePort.getChatRoomListBy(chatRoomSaveCommand.getArticleId()).stream()
            .map(ChatRoomDocument::getId)
            .toList();
    }

}



package chat.application;

import chat.adapter.output.client.ArticleClient;
import chat.adapter.output.client.UserClient;
import chat.adapter.output.client.dto.ArticleFeignInfo;
import chat.application.port.input.UserChatSaveUseCase;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.domain.command.ChatRoomSaveCommand;
import chat.domain.dto.ChatRoomSaveForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomSaveService {

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    private final UserChatSaveUseCase userChatSaveUseCase;

    private final ChatRoomGenerator chatRoomGenerator;

    private final ArticleClient articleClient;

    private final UserClient userClient;

    public String saveChatRoom(ChatRoomSaveCommand chatRoomSaveCommand) {
        String buyerId = chatRoomSaveCommand.getBuyerId();
        ArticleFeignInfo articleInfo = getArticleBy(chatRoomSaveCommand.getArticleId());

        // articleId로 ChatRoom 존재 여부 확인, 없으면 생성
        String chatRoomId = chatRoomPersistencePort.getChatRoomBy(chatRoomSaveCommand.getArticleId())
            .orElseGet(() ->
                createChatRoom(buyerId, chatRoomSaveCommand.getArticleId(), articleInfo)
            );

        // UserChat 존재 유뮤 확인, 없으면 생성
        userChatSaveUseCase.saveUserChatIfNotExists(chatRoomId, buyerId);
        userChatSaveUseCase.saveUserChatIfNotExists(chatRoomId, articleInfo.getUserId());

        return chatRoomId;
    }

    private ArticleFeignInfo getArticleBy(String articleId) {
        return articleClient.getArticleBy(articleId);
    }

    private String createChatRoom(String buyerId, String articleId, ArticleFeignInfo articleInfo) {
        String buyerNickName = userClient.getNickname(buyerId);
        String sellerNickName = userClient.getNickname(articleInfo.getUserId());

        String title = chatRoomGenerator.generateDefaultTitle(
            List.of(buyerNickName, sellerNickName));

        return chatRoomPersistencePort.saveChatRoom(
            ChatRoomSaveForm.of(title, articleId, articleInfo.getArticleImage()));
    }

}

package chat.application;

import chat.adapter.output.client.ArticleClient;
import chat.adapter.output.client.UserClient;
import chat.adapter.output.client.response.ArticleFeignResponse;
import chat.application.port.input.ChatRoomCheckUseCase;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.application.port.output.UserChatPersistencePort;
import chat.domain.command.ChatRoomSaveCommand;
import chat.domain.dto.ChatRoomSaveForm;
import chat.domain.dto.UserChatSaveForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomSaveService {

    private final ChatRoomCheckUseCase chatRoomCheckUseCase;

    private final UserChatPersistencePort userChatPersistencePort;

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    private final ChatRoomGenerator chatRoomGenerator;

    private final ArticleClient articleClient;

    private final UserClient userClient;

    public String saveChatRoom(ChatRoomSaveCommand chatRoomSaveCommand) {
        String buyerId = chatRoomSaveCommand.getBuyerId();
        ArticleFeignResponse articleInfo = getArticleBy(chatRoomSaveCommand.getArticleId());

        // article userId => sellerId
        String chatRoomId = createChatRoom(buyerId, articleInfo);

        saveUserChat(chatRoomId, buyerId);
        saveUserChat(chatRoomId, articleInfo.getUserId());

        return chatRoomId;
    }

    private ArticleFeignResponse getArticleBy(String articleId) {
        return articleClient.getArticleBy(articleId);
    }

    private String createChatRoom(String buyerId, ArticleFeignResponse articleInfo) {
        String buyerNickName = userClient.getNickname(buyerId);
        String sellerNickName = userClient.getNickname(articleInfo.getUserId());

        String title = chatRoomGenerator.generateDefaultTitle(
            List.of(buyerNickName, sellerNickName));

        return chatRoomPersistencePort.saveChatRoom(
            ChatRoomSaveForm.of(title, articleInfo.getArticleImage()));
    }

    private void saveUserChat(String chatRoomId, String userId) {
        userChatPersistencePort.saveUserChat(UserChatSaveForm.of(chatRoomId, userId));
    }

}

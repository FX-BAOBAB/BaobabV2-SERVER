package chat.application.chatroom;

import chat.adapter.output.client.ArticleClient;
import chat.adapter.output.client.UserClient;
import chat.adapter.output.client.dto.ArticleFeignInfo;
import chat.domain.command.ChatRoomReaderCommand;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractChatRoomGenerator {

    private final ArticleClient articleClient;

    private final UserClient userClient;

    public final ChatRoom createChatRoom(ChatRoomReaderCommand command) {

        ArticleFeignInfo articleBy = articleClient.getArticleBy(command.getArticleId());

        String sellerNickName = userClient.getUserSimpleInfo(command.getBuyerId()).getNickname();
        String articleOwnerNickName = userClient.getUserSimpleInfo(articleBy.getUserId()).getNickname();

        // 채팅방 이름 설정
        String chatRoomTitle = buildChatRoomTitle(List.of(sellerNickName, articleOwnerNickName));

        return ChatRoom.builder()
            .title(chatRoomTitle)
            .articleId(command.getArticleId())
            .thumbnailId(articleBy.getArticleImage().getImageId())
            .build();
    }

    protected abstract String buildChatRoomTitle(List<String> userNickNameList);

}


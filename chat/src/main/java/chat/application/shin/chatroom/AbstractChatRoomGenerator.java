package chat.application.shin.chatroom;

import chat.adapter.output.client.ArticleClient;
import chat.adapter.output.client.UserClient;
import chat.adapter.output.client.dto.ArticleFeignInfo;
import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.application.shin.factory.ChatRoomTitleGenerator;
import chat.domain.command.ChatRoomSaveCommand;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractChatRoomGenerator {

    private final ArticleClient articleClient;

    private final UserClient userClient;

    public final ChatRoom createChatRoom(ChatRoomSaveCommand command){

        ArticleFeignInfo articleBy = articleClient.getArticleBy(command.getArticleId());
        String articleOwnerId = articleBy.getUserId();

        // 채팅방 이름 설정
        String chatRoomTitle = buildChatRoomTitle(List.of(command.getBuyerId(), articleOwnerId));

        return  ChatRoom.builder()
                .title(chatRoomTitle)
                .articleId(command.getArticleId())
                .thumbnailId(articleBy.getArticleImage().getImageId())
                .build();
    }

    protected abstract String buildChatRoomTitle(List<String> userNickNameList);
}


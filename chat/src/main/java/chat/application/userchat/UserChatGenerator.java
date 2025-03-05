package chat.application.userchat;

import chat.adapter.output.client.ArticleClient;
import chat.domain.command.ChatRoomReaderCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserChatGenerator {

    private final ArticleClient articleClient;

    public List<UserChat> createUserChat(String chatRoomId, ChatRoomReaderCommand command) {

        String sellerId = articleClient.getArticleBy(command.getArticleId()).getUserId();

        UserChat sellerChat = UserChat.builder()
            .userId(sellerId)
            .chatRoomId(chatRoomId)
            .build();

        UserChat buyerChat = UserChat.builder()
            .userId(command.getBuyerId())
            .chatRoomId(chatRoomId)
            .build();

        return List.of(sellerChat, buyerChat);
    }


}

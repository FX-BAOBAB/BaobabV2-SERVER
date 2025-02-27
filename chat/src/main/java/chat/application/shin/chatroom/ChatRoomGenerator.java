package chat.application.shin.chatroom;

import chat.adapter.output.client.ArticleClient;
import chat.adapter.output.client.UserClient;
import chat.application.shin.factory.ChatRoomTitleGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatRoomGenerator extends AbstractChatRoomGenerator {

    private final ChatRoomTitleGenerator titleGenerator;

    public ChatRoomGenerator(ChatRoomTitleGenerator titleGenerator, ArticleClient articleClient, UserClient userClient) {
        super(articleClient, userClient);
        this.titleGenerator = titleGenerator;
    }

    @Override
    protected String buildChatRoomTitle(List<String> userNickNameList) {
        return titleGenerator.getChatRoomTitle(userNickNameList);
    }

}

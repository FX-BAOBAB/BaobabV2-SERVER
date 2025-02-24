package chat.application;

import chat.application.stragety.ChatRoomTitleStrategy;
import java.util.List;
import lombok.Setter;

@Setter
public class ChatRoomGenerator {

    private ChatRoomTitleStrategy chatRoomChatRoomTitleStrategy;

    public String generateTitle(List<String> nickNameList) {
        return chatRoomChatRoomTitleStrategy.generateTitle(nickNameList);
    }

}

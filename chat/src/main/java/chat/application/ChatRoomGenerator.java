package chat.application;

import chat.application.stragety.ChatRoomTitleStrategy;
import chat.application.stragety.NormalTitleGenerator;
import chat.application.stragety.TruncatedTitleGenerator;
import java.util.List;
import lombok.Setter;

@Setter
public class ChatRoomGenerator {

    private ChatRoomTitleStrategy chatRoomChatRoomTitleStrategy;

    private static final int MAX_USER_TITLE = 5;

    public String generateDefaultTitle(List<String> nickNameList) {
        if (nickNameList.size() > MAX_USER_TITLE) {
            this.setChatRoomChatRoomTitleStrategy(new TruncatedTitleGenerator());
        } else {
            this.setChatRoomChatRoomTitleStrategy(new NormalTitleGenerator());
        }
        return chatRoomChatRoomTitleStrategy.generateTitle(nickNameList);
    }

}

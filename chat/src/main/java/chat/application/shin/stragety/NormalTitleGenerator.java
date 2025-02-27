package chat.application.shin.stragety;

import java.util.List;

public class NormalTitleGenerator implements ChatRoomTitleStrategy {

    @Override
    public String generateTitle(List<String> uesrNickNameList) {
        return String.join(", ", uesrNickNameList) + "의 채팅방";
    }

}

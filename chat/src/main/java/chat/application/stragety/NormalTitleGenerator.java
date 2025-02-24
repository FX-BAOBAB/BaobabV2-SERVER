package chat.application.stragety;

import java.util.List;

public class NormalTitleGenerator implements ChatRoomTitleStrategy {

    @Override
    public String generateTitle(List<String> userNickNameList) {
        return String.join(", ", userNickNameList) + "의 채팅방";
    }

}

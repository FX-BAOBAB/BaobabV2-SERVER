package chat.application.stragety;

import java.util.List;

public class NormalTitleGenerator implements ChatRoomTitleStrategy {

    @Override
    public String generateTitle(List<Long> userNickNameList) {
        return String.join(", ", userNickNameList.toString()) + "의 채팅방";
    }

}

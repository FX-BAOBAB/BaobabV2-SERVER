package chat.application.stragety;

import java.util.List;

public class TruncatedTitleGenerator implements ChatRoomTitleStrategy {

    @Override
    public String generateTitle(List<Long> userNickNameList) {
        return userNickNameList.get(0) + "님의 단톡방";
    }

}

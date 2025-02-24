package chat.application.stragety;

import java.util.List;

public class TruncatedTitleGenerator implements ChatRoomTitleStrategy {

    private static final int MAX_USER_TITLE = 5;

    @Override
    public String generateTitle(List<Long> userNickNameList) {
        return String.join(", ", userNickNameList.subList(0, MAX_USER_TITLE).toString())
            + " 외 " + (userNickNameList.size() - MAX_USER_TITLE) + "명의 채팅방";
    }

}

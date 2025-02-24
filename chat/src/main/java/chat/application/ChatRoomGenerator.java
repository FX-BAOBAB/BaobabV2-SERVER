package chat.application;

import java.util.List;

public class ChatRoomGenerator implements ChatRoomTitleGeneratorUseCase {

    private static final int MAX_USER_TITLE = 5;

    @Override
    public String generateDefaultTitle(List<String> userNickNameList) {
        if (userNickNameList.size() > MAX_USER_TITLE) {
            return truncateTitle(userNickNameList);
        }
        return generateNormalTitle(userNickNameList);
    }

    private String truncateTitle(List<String> userNickNameList) {
        return generateTitle(userNickNameList.subList(0, MAX_USER_TITLE))
            + " 외 " + (userNickNameList.size() - MAX_USER_TITLE) + "명의 채팅방";
    }

    private String generateNormalTitle(List<String> userNickNameList) {
        return generateTitle(userNickNameList) + "의 채팅방";
    }

    private String generateTitle(List<String> userNickNameList) {
        return String.join(", ", userNickNameList);
    }

}

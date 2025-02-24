package chat.application;

import java.util.List;

public class ChatRoomGenerator implements ChatRoomTitleGeneratorUseCase {

    private static final int MAX_USER_TITLE = 5;

    @Override
    public String generateDefaultTitle(List<String> userNickNameList) {
        if (userNickNameList.size() > MAX_USER_TITLE) {
            return generateTitle(userNickNameList.subList(0, MAX_USER_TITLE))
                + " 외 " + (userNickNameList.size() - MAX_USER_TITLE) + "명의 채팅방";
        }
        return generateTitle(userNickNameList) + "의 채팅방";
    }

    private String generateTitle(List<String> userNickNameList) {
        StringBuilder titleBuilder = new StringBuilder();
        boolean first = true;

        for (String userNickName : userNickNameList) {
            if (!first) {
                titleBuilder.append(", ");
            }
            titleBuilder.append(userNickName);
            first = false;
        }

        return titleBuilder.toString();
    }
}

package chat.application;

import java.util.List;

public class ChatRoomGenerator {

    public String generateTitle(List<String> userNickNameList) {

        StringBuilder titleBuilder = new StringBuilder();
        boolean first = true;

        for (String userNickName : userNickNameList) {
            if (!first) {
                titleBuilder.append(", ");
            }
            titleBuilder.append(userNickName);
            first = false;
        }

        titleBuilder.append("의 채팅방");
        return titleBuilder.toString();
    }

}

package chat.application.factory;

import java.util.List;

public interface ChatRoomTitleGenerator {

    String getChatRoomTitle(List<String> userNickNameList);

}

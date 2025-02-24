package chat.application;

import java.util.List;

public interface ChatRoomTitleGeneratorUseCase {

    String generateDefaultTitle(List<String> userNickNameList);

}

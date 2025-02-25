package chat.application.stragety;

import java.util.List;

public interface ChatRoomTitleStrategy {

    String generateTitle(List<Long> nickNameList);

}

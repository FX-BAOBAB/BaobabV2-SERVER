package chat.application.port.input;

import java.util.List;
import java.util.Optional;

public interface ChatRoomCheckUseCase {

    Optional<String> existsChatRoomBy(List<String> chatRoomIdList, String userId);

}

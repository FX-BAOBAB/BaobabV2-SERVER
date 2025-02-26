package chat.application.port.output;

import chat.domain.dto.ChatRoomSaveForm;

public interface ChatRoomPersistencePort {

    boolean existsChatRoomBy(String chatRoomId);

    String saveChatRoom(ChatRoomSaveForm chatRoomSaveForm);

}

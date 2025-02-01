package chat.application.port.output;

import chat.adapter.output.persistence.repository.ChatRoomDocument;
import chat.domain.dto.ChatRoomSaveForm;

public interface ChatRoomPersistencePort {

    boolean saveChatRoom(ChatRoomSaveForm chatRoomSaveForm);

    ChatRoomDocument getChatRoomList(String userId);

}

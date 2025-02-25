package chat.application;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;

import java.util.List;

public interface ChatRoomFactory {

    ChatRoomDocument createChatRoom(List<Long> userIdList);

}

package chat.application.factory;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;

import java.util.List;

public interface ChatRoomFactory {

    ChatRoomDocument createChatRoom(List<String> userNickNameList);

}

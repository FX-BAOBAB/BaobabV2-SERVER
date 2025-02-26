package chat.adapter.output;

import chat.adapter.output.persistence.repository.ChatRoomMongoRepository;
import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.domain.dto.ChatRoomSaveForm;
import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements ChatRoomPersistencePort {

    private final ChatRoomMongoRepository chatRoomMongoRepository;

    @Override
    public boolean existsChatRoomBy(String chatRoomId) {
        return chatRoomMongoRepository.existsById(chatRoomId);
    }

    @Override
    public String saveChatRoom(ChatRoomSaveForm chatRoomSaveForm) {
        ChatRoomDocument savedChatRoom = chatRoomMongoRepository.save(
            ChatRoomDocument.of(chatRoomSaveForm));
        return savedChatRoom.getId();
    }

}

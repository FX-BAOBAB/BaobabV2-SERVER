package chat.adapter.output.persistence;

import chat.adapter.output.persistence.repository.ChatRoomDocument;
import chat.adapter.output.persistence.repository.ChatRoomMongoRepository;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.domain.dto.ChatRoomSaveForm;
import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements ChatRoomPersistencePort {

    private final ChatRoomMongoRepository chatRoomMongoRepository;

    @Override
    public boolean saveChatRoom(ChatRoomSaveForm chatRoomSaveForm) {
        ChatRoomDocument savedChatRoom =  chatRoomMongoRepository.save(ChatRoomDocument.of(chatRoomSaveForm));
        return savedChatRoom.getId() != null;
    }

    @Override
    public boolean existsChatRoom(String articleId, String buyerId) {
        return chatRoomMongoRepository.existsByArticleIdAndBuyerId(articleId, buyerId);
    }

    @Override
    public ChatRoomDocument getChatRoomList(String userId) {
        return null;
    }

}

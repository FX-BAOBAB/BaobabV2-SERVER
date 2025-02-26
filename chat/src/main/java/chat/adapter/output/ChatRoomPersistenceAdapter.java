package chat.adapter.output;

import chat.adapter.output.persistence.repository.ChatRoomMongoRepository;
import chat.application.port.output.ChatRoomPersistencePort;
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

}

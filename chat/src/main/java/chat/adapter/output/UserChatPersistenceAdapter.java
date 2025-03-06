package chat.adapter.output;

import chat.adapter.output.persistence.repository.UserChatMongoRepository;
import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.application.port.output.UserChatPersistencePort;
import chat.domain.dto.UserChatSaveForm;
import global.annotation.output.PersistenceAdapter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserChatPersistenceAdapter implements UserChatPersistencePort {

    private final UserChatMongoRepository userChatMongoRepository;

    public void saveUserChat(UserChatSaveForm userChatSaveForm) {
        userChatMongoRepository.save(UserChatDocument.of(userChatSaveForm));
    }

    @Override
    public Boolean existsBy(String chatRoomId, String userId) {
        return userChatMongoRepository.existsByChatRoomIdAndUserId(chatRoomId, userId);
    }

    @Override
    public Optional<UserChatDocument> getUserChat(String chatRoomId, String userId) {
        return userChatMongoRepository.findByChatRoomIdAndUserId(chatRoomId, userId);
    }

    /**
     * sender 를 제외한 UserChat 조회
     *
     * @param chatRoomId
     * @param senderId
     * @return
     */
    @Override
    public List<UserChatDocument> getUserChatsExcludingSender(String chatRoomId, String senderId) {
        return userChatMongoRepository.findByChatRoomIdAndUserIdNot(chatRoomId, senderId);
    }

}

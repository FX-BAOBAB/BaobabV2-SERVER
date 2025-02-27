package chat.adapter.output;

import chat.adapter.output.persistence.repository.UserChatMongoRepository;
import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.application.port.output.UserChatPersistencePort;
import chat.domain.dto.UserChatSaveForm;
import global.annotation.output.PersistenceAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserChatPersistenceAdapter implements UserChatPersistencePort {

    private final UserChatMongoRepository userChatMongoRepository;

    public Boolean saveUserChat(UserChatSaveForm userChatSaveForm) {
        UserChatDocument savedUserChat = userChatMongoRepository.save(
            UserChatDocument.of(userChatSaveForm));
        return savedUserChat.getUserId() != null;
    }

    @Override
    public Boolean existsBy(String chatRoomId, String userId) {
        return userChatMongoRepository.existsByChatRoomIdAndUserId(chatRoomId, userId);
    }

    @Override
    public Optional<UserChatDocument> getUserChat(String chatRoomId, String userId) {
        return userChatMongoRepository.findByChatRoomIdAndUserId(chatRoomId, userId);
    }

}

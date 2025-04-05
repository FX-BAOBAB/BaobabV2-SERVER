package chat.adapter.output;

import chat.adapter.output.persistence.repository.UserChatMongoRepository;
import chat.adapter.output.persistence.repository.UserChatQueryRepository;
import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.application.port.output.UserChatPersistencePort;
import chat.domain.dto.ChatRoomSearchForm;
import chat.domain.dto.UserChatSaveForm;
import chat.domain.dto.UserChatUpdateForm;
import global.annotation.output.PersistenceAdapter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserChatPersistenceAdapter implements UserChatPersistencePort {

    private final UserChatMongoRepository userChatMongoRepository;
    private final UserChatQueryRepository userChatQueryRepository;

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

    @Override
    public List<UserChatDocument> getUserChatList(String chatRoomId, List<String> userIdList) {
        return userChatMongoRepository.findByChatRoomIdAndUserIdIn(chatRoomId, userIdList);
    }

    @Override
    public void updateUserChat(List<UserChatUpdateForm> userChatUpdateFormList) {
        userChatUpdateFormList.forEach(userChatUpdateForm ->
            userChatMongoRepository.save(UserChatDocument.of(userChatUpdateForm))
        );
    }

    @Override
    public List<UserChatDocument> getUserChats(ChatRoomSearchForm chatRoomSearchForm) {
        return userChatQueryRepository.getUserChats(chatRoomSearchForm);
    }

}

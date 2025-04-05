package chat.adapter.output.persistence.repository;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserChatMongoRepository extends MongoRepository<UserChatDocument, String> {

    boolean existsByChatRoomIdAndUserId(String chatRoomId, String userId);

    Optional<UserChatDocument> findByChatRoomIdAndUserId(String chatRoomId, String userId);

    List<UserChatDocument> findByChatRoomIdAndUserIdNot(String chatRoomId, String userId);

    List<UserChatDocument> findByChatRoomIdAndUserIdIn(String chatRoomId, List<String> userIdList);

}

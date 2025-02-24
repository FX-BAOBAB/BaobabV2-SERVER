package chat.adapter.output.persistence.repository;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserChatMongoRepository extends MongoRepository<UserChatDocument, String> {

}

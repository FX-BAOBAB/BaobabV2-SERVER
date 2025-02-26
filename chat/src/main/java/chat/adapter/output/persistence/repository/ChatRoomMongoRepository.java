package chat.adapter.output.persistence.repository;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomMongoRepository extends MongoRepository<ChatRoomDocument, String> {

    ChatRoomDocument findByArticleId(String articleId);

}

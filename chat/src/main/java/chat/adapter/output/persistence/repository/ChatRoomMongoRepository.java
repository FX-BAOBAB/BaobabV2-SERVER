package chat.adapter.output.persistence.repository;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomMongoRepository extends MongoRepository<ChatRoomDocument, String> {

    List<ChatRoomDocument> findByArticleId(String articleId);

    List<ChatRoomDocument> findByIdIn(List<String> chatRoomIdList);

}

package chat.adapter.output.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomMongoRepository extends MongoRepository<ChatRoomDocument, String> {

    boolean existsByArticleIdAndBuyerId(String articleId, String buyerId);

}

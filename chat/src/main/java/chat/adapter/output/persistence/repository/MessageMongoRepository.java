package chat.adapter.output.persistence.repository;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageMongoRepository extends MongoRepository<MessageDocument, String> {

}

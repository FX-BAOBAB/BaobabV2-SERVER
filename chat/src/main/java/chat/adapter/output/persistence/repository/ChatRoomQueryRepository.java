package chat.adapter.output.persistence.repository;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomQueryRepository extends QuerydslRepositorySupport {

    public ChatRoomQueryRepository(@Qualifier("mongoTemplate") MongoOperations operations) {
        super(operations);
    }

    public List<ChatRoomDocument> getChatRoomListBy() {
        return null;
    }

}

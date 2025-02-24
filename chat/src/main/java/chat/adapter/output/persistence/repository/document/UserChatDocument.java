package chat.adapter.output.persistence.repository.document;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_chat")
@CompoundIndex(def = "{'userId': 1, 'chatRoomId': 1}", unique = true) // 복합키 설정
public class UserChatDocument {

    @Id
    private String id;

    private String userId;

    private String chatRoomId;

    private Boolean mute;

    private Boolean favorite;

    private LocalDateTime lastChatAt;

}

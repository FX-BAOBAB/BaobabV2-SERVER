package chat.adapter.output.persistence.repository.document;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_chat")
public class UserChatDocument {

    private String userId;

    private String chatRoomId;

    private Boolean mute;

    private Boolean favorite;

    private LocalDateTime lastChatAt;

}

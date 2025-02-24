package chat.adapter.output.persistence.repository.document;

import chat.adapter.output.persistence.enums.MessageType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_message")
public class MessageDocument {

    @Id
    private String id;

    private String message;

    private MessageType messageType;

    private LocalDateTime sendAt;

    private Boolean isRead;

    private String chatId;

}

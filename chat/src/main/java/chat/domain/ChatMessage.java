package chat.domain;

import chat.adapter.output.persistence.enums.MessageType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private String userId;

    private String message;

    private MessageType messageType;

    private LocalDateTime sentAt;

    private Boolean isRead;

    private String chatRoomId;

}

package chat.adapter.input.web.request;

import chat.adapter.output.persistence.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageSearchCondition {

    @NotBlank
    private String chatRoomId;

    private String message;

    private MessageType messageType;

    private LocalDateTime sentAt;

}

package chat.adapter.input.web.request;

import chat.adapter.output.persistence.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {

    @NotBlank
    private String message;

    @NotNull
    private MessageType messageType;

    @NotBlank
    private String chatRoomId;

}

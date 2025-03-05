package chat.adapter.input.web.request;

import chat.adapter.output.persistence.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest { // TODO 유효성 검증

    private String message;

    private MessageType messageType;

    private String chatRoomId;

}

package chat.domain.command;

import chat.adapter.output.persistence.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatMessagePublishCommand {

    private String message;

    private String chatRoomId;

    private String userId;

    private MessageType messageType;

}
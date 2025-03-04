package chat.domain.dto;

import chat.adapter.output.persistence.enums.MessageType;
import chat.domain.command.ChatMessagePublishCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatMessageSaveForm {

    private String message;

    private String chatRoomId;

    private String userId;

    private MessageType messageType;

    public static ChatMessageSaveForm of(ChatMessagePublishCommand command) {
        return ChatMessageSaveForm.builder()
            .message(command.getMessage())
            .chatRoomId(command.getChatRoomId())
            .userId(command.getUserId())
            .messageType(command.getMessageType())
            .build();

    }
}

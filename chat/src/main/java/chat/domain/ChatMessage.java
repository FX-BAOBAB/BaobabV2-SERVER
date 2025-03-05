package chat.domain;

import chat.adapter.output.persistence.enums.MessageType;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.command.ChatMessagePublishCommand;
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

    public static ChatMessage of(MessageDocument document, String userId) {
        return ChatMessage.builder()
            .userId(userId)
            .message(document.getMessage())
            .messageType(document.getMessageType())
            .sentAt(document.getSendAt())
            .isRead(document.getIsRead())
            .chatRoomId(document.getChatRoomId())
            .build();
    }

}

package chat.adapter.output.persistence.repository.document;

import chat.adapter.output.persistence.enums.MessageType;
import chat.domain.dto.ChatMessageSaveForm;
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

    @Builder.Default
    private LocalDateTime sendAt = LocalDateTime.now();

    @Builder.Default
    private Boolean isRead = false;

    private String chatRoomId;

    public static MessageDocument of(ChatMessageSaveForm chatMessageSaveForm) {
        return MessageDocument.builder()
            .message(chatMessageSaveForm.getMessage())
            .messageType(chatMessageSaveForm.getMessageType())
            .chatRoomId(chatMessageSaveForm.getChatRoomId())
            .build();
    }

}

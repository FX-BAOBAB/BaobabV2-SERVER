package chat.domain;

import chat.adapter.output.persistence.enums.MessageType;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private String senderId;

    private List<String> receiverIdList;

    private String message;

    private MessageType messageType;

    private LocalDateTime sentAt;

    private Boolean isRead;

    private String chatRoomId;

    public static ChatMessage of(MessageDocument document, String userId, List<String> receiverIdList) {
        return ChatMessage.builder()
            .senderId(userId)
            .receiverIdList(receiverIdList)
            .message(document.getMessage())
            .messageType(document.getMessageType())
            .sentAt(document.getSentAt())
            .isRead(document.getIsRead())
            .chatRoomId(document.getChatRoomId())
            .build();
    }

}

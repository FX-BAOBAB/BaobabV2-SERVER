package chat.domain.command;

import chat.adapter.input.web.request.ChatMessageSearchCondition;
import chat.adapter.output.persistence.enums.MessageType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
public class ChatMessageSearchCommand {

    private String chatRoomId;

    private String message;

    private MessageType messageType;

    private LocalDateTime sentAt;

    private Pageable pageable;

    private String userId;

    public static ChatMessageSearchCommand of(
        String userId, ChatMessageSearchCondition condition, Pageable pageable
    ) {
        return ChatMessageSearchCommand.builder()
            .chatRoomId(condition.getChatRoomId())
            .message(condition.getMessage())
            .messageType(condition.getMessageType())
            .sentAt(condition.getSentAt())
            .pageable(pageable)
            .userId(userId)
            .build();
    }

}

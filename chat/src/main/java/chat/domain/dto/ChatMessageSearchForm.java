package chat.domain.dto;

import chat.adapter.input.web.request.ChatMessageSearchCondition;
import chat.adapter.output.persistence.enums.MessageType;
import chat.domain.command.ChatMessageSearchCommand;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
public class ChatMessageSearchForm {

    private String chatRoomId;

    private String message;

    private MessageType messageType;

    private LocalDateTime sentAt;

    private Pageable pageable;

    public static ChatMessageSearchForm of(ChatMessageSearchCommand command) {
        return ChatMessageSearchForm.builder()
            .chatRoomId(command.getChatRoomId())
            .message(command.getMessage())
            .messageType(command.getMessageType())
            .sentAt(command.getSentAt())
            .pageable(command.getPageable())
            .build();
    }

}

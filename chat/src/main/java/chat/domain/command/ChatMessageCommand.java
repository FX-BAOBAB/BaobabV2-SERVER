package chat.domain.command;

import chat.adapter.input.web.request.ChatMessageRequest;
import chat.adapter.output.persistence.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatMessageCommand {

    private String message;

    private String chatRoomId;

    private String userId;

    private MessageType messageType;

    public static ChatMessageCommand of(ChatMessageRequest request, String userId) {
        return ChatMessageCommand.builder()
            .message(request.getMessage())
            .chatRoomId(request.getChatRoomId())
            .userId(userId)
            .messageType(request.getMessageType())
            .build();
    }

}
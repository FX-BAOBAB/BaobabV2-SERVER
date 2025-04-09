package chat.adapter.input.web.response;

import chat.adapter.output.persistence.enums.MessageType;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.dto.UserSimpleInfo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse {

    private String id;

    private String message;

    private MessageType messageType;

    private LocalDateTime sentAt = LocalDateTime.now();

    private Boolean isRead = false;

    private String chatRoomId;

    private String nickname;

    private String profileImageUrl;

    private Boolean isMine;

    public static ChatMessageResponse of(
        MessageDocument messageDocument,
        UserSimpleInfo userInfo,
        boolean isMine
    ) {
        return ChatMessageResponse.builder()
            .id(messageDocument.getId())
            .message(messageDocument.getMessage())
            .messageType(messageDocument.getMessageType())
            .sentAt(messageDocument.getSentAt())
            .isRead(messageDocument.getIsRead())
            .chatRoomId(messageDocument.getChatRoomId())
            .nickname(userInfo.getNickname())
            .profileImageUrl(userInfo.getProfileImageUrl())
            .isMine(isMine)
            .build();
    }

}

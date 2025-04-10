package chat.domain;

import chat.adapter.output.persistence.enums.MessageType;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.dto.UserSimpleInfo;
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

    // message id
    private String id;

    private String senderId;

    private List<String> receiverIdList;

    private String message;

    private MessageType messageType;

    private LocalDateTime sentAt;

    private Boolean isRead;

    private String chatRoomId;

    private String nickname;

    private String profileImageUrl;

    public static ChatMessage of(
        MessageDocument document,
        String userId,
        UserSimpleInfo userSimpleInfo,
        List<String> receiverIdList
    ) {
        return ChatMessage.builder()
            .id(document.getId())
            .senderId(userId)
            .receiverIdList(receiverIdList)
            .message(document.getMessage())
            .messageType(document.getMessageType())
            .sentAt(document.getSentAt())
            .isRead(document.getIsRead())
            .chatRoomId(document.getChatRoomId())
            .nickname(userSimpleInfo.getNickname())
            .profileImageUrl(userSimpleInfo.getProfileImageUrl())
            .build();
    }

}

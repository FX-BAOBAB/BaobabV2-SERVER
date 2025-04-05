package chat.domain.dto;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserChatUpdateForm {

    private String id;

    private String userId;

    private String chatRoomId;

    private Boolean mute;

    private Boolean favorite;

    private LocalDateTime lastChatAt;

    public static List<UserChatUpdateForm> of(List<UserChatDocument> userChatDocumentList) {
        return userChatDocumentList.stream()
            .map(userChatDocument -> UserChatUpdateForm.builder()
                .id(userChatDocument.getId())
                .userId(userChatDocument.getUserId())
                .chatRoomId(userChatDocument.getChatRoomId())
                .mute(userChatDocument.getMute())
                .favorite(userChatDocument.getFavorite())
                .lastChatAt(userChatDocument.getLastChatAt())
                .build())
            .toList();
    }

}

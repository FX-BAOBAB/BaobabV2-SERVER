package chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserChatSaveForm {

    private String chatRoomId;

    private String userId;

    public static UserChatSaveForm of(String chatRoomId, String userId) {
        return UserChatSaveForm.builder()
            .chatRoomId(chatRoomId)
            .userId(userId)
            .build();
    }

}


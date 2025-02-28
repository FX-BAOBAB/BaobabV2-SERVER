package chat.domain.dto;

import chat.application.userchat.UserChat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserChatSaveForm {

    private String chatRoomId;

    private String userId;

    public static List<UserChatSaveForm> of(List<UserChat> userChats) {
        return userChats.stream()
            .map(userChat -> UserChatSaveForm.builder()
                .chatRoomId(userChat.getChatRoomId())
                .userId(userChat.getUserId())
                .build())
            .toList();
    }

}


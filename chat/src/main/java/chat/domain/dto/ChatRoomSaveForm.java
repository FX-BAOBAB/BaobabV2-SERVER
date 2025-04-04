package chat.domain.dto;

import chat.application.chatroom.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomSaveForm {

    private String title;

    private String articleId;

    private String thumbnailUrl;

    public static ChatRoomSaveForm of(ChatRoom chatRoom) {
        return ChatRoomSaveForm.builder()
            .title(chatRoom.getTitle())
            .articleId(chatRoom.getArticleId())
            .thumbnailUrl(chatRoom.getThumbnailUrl())
            .build();
    }
}

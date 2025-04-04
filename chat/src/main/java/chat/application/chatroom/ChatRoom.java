package chat.application.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    private Long id;

    private String title;

    private String articleId;

    private String thumbnailUrl;

}

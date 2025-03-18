package chat.adapter.input.web.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponse {

    private String chatRoomId;

    private String title;

    private String articleId;

    private String thumbnailId;

    private LocalDateTime lastChatAt;

}

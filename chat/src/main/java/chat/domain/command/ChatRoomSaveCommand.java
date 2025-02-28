package chat.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomSaveCommand {

    private String articleId;

    private String buyerId;

    public static ChatRoomSaveCommand of(String articleId, String buyerId) {
        return ChatRoomSaveCommand.builder()
            .articleId(articleId)
            .buyerId(buyerId)
            .build();
    }

}

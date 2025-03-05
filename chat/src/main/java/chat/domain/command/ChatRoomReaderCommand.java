package chat.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomReaderCommand {

    private String articleId;

    private String buyerId;

    public static ChatRoomReaderCommand of(String articleId, String buyerId) {
        return ChatRoomReaderCommand.builder()
            .articleId(articleId)
            .buyerId(buyerId)
            .build();
    }

}

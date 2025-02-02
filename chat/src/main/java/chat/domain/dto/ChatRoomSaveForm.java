package chat.domain.dto;

import chat.domain.command.ChatRoomSaveCommand;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomSaveForm {

    private String articleId;

    private String buyerId;

    private LocalDateTime registeredAt;

    public static ChatRoomSaveForm of(ChatRoomSaveCommand chatRoomSaveCommand) {
        return ChatRoomSaveForm.builder()
            .articleId(chatRoomSaveCommand.getArticleId())
            .buyerId(chatRoomSaveCommand.getBuyerId())
            .registeredAt(LocalDateTime.now())
            .build();
    }

}

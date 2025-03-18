package chat.domain.command;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomSearchCommand {

    private String userId;

    private LocalDateTime lastChatAt;

    private Pageable pageable;

    public static ChatRoomSearchCommand of(
        String userId, LocalDateTime lastChatAt, Pageable pageable
    ) {
        return ChatRoomSearchCommand.builder()
            .userId(userId)
            .lastChatAt(lastChatAt)
            .pageable(pageable)
            .build();
    }

}

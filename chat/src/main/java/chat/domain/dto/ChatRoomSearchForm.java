package chat.domain.dto;

import chat.domain.command.ChatRoomSearchCommand;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomSearchForm {

    private String userId;

    private LocalDateTime lastChatAt;

    private Pageable pageable;

    public static ChatRoomSearchForm of(ChatRoomSearchCommand command) {
        return ChatRoomSearchForm.builder()
            .userId(command.getUserId())
            .lastChatAt(command.getLastChatAt())
            .pageable(command.getPageable())
            .build();
    }

}

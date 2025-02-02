package chat.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomSaveForm {

    private String articleId;

    private String buyerId;

    private LocalDateTime registeredAt;


}

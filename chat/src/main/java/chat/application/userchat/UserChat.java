package chat.application.userchat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserChat {

    private String userId;

    private String chatRoomId;

}

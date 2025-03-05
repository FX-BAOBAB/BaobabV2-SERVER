package chat.adapter.input.web.response;

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

    public static ChatRoomResponse of(String chatRoomId) {
        return ChatRoomResponse.builder()
            .chatRoomId(chatRoomId)
            .build();
    }

}

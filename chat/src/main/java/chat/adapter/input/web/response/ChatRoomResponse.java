package chat.adapter.input.web.response;

import chat.application.sse.UserSseConnection;
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

    private UserSseConnection connection;

    public static ChatRoomResponse of(String chatRoomId, UserSseConnection connection) {
        return ChatRoomResponse.builder()
            .chatRoomId(chatRoomId)
            .connection(connection)
            .build();
    }

}

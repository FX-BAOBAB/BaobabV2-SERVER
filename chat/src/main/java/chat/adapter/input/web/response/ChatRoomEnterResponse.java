package chat.adapter.input.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomEnterResponse {

    private String chatRoomId;

    private SseEmitter sseEmitter;

    public static ChatRoomEnterResponse of(String chatRoomId, SseEmitter sseEmitter) {
        return ChatRoomEnterResponse.builder()
            .chatRoomId(chatRoomId)
            .sseEmitter(sseEmitter)
            .build();
    }

}

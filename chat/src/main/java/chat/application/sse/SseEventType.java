package chat.application.sse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SseEventType {

    CHAT("채팅")
    ;

    private String description;

}

package chat.application.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Component
@RequiredArgsConstructor
public class SseMessageManager {

    private final ObjectMapper objectMapper;

    public void sendMessage(UserSseConnection connection, String eventName, Object data) {
        send(connection, eventName, data);
    }

    public void sendMessage(UserSseConnection connection, Object data) {
        send(connection, null, data);
    }


    public void send(UserSseConnection connection, String eventName, Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            SseEventBuilder event = SseEmitter.event().data(json);
            if (eventName != null) {
                event.name(eventName);
            }
            connection.getSseEmitter().send(event);
        } catch (IOException e) {
            connection.getSseEmitter().completeWithError(e);
        }
    }

}
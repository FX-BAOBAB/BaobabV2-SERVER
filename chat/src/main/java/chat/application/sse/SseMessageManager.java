package chat.application.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseMessageManager {

    private final ObjectMapper objectMapper;

    public void sendMessage(UserSseConnection connection, SseEventType eventType, Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                .name(eventType.name())
                .data(json)
                ;

            connection.getSseEmitter().send(event);
        } catch (IOException e) {
            connection.getSseEmitter().completeWithError(e);
        }
    }

}
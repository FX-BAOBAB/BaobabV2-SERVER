package chat.application.sse;

import chat.application.port.output.SseConnectionRegistryPort;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String userId;
    private final SseEmitter sseEmitter;
    private final SseConnectionRegistryPort<String, UserSseConnection> connectionPoolPort;

    private UserSseConnection(
        String userId,
        SseConnectionRegistryPort<String, UserSseConnection> connectionPoolPort
    ){
        this.userId = userId;
        this.sseEmitter = new SseEmitter(60 * 1000L * 60); // 1h
        this.connectionPoolPort = connectionPoolPort; // call back 초기화

        this.sseEmitter.onCompletion(()->{
            this.connectionPoolPort.deleteEmitter(this);
        });

        this.sseEmitter.onTimeout(this.sseEmitter::complete);
    }

    public static UserSseConnection create(
        String userId,
        SseConnectionRegistryPort<String, UserSseConnection> connectionPoolPort
    ){
        return new UserSseConnection(userId, connectionPoolPort);
    }

}
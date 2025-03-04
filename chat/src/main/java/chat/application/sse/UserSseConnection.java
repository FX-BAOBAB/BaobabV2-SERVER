package chat.application.sse;

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
    private final SseConnectionStore<String, UserSseConnection> sseConnectionStore;

    private UserSseConnection(
        String userId,
        SseConnectionStore<String, UserSseConnection> connectionRegistryPort
    ){
        this.userId = userId;
        this.sseEmitter = new SseEmitter(60 * 1000L * 60); // 1h
        this.sseConnectionStore = connectionRegistryPort; // call back 초기화

        this.sseEmitter.onCompletion(()->{
            this.sseConnectionStore.deleteEmitter(this);
        });

        this.sseEmitter.onTimeout(this.sseEmitter::complete);
    }

    public static UserSseConnection create(
        String userId,
        SseConnectionStore<String, UserSseConnection> connectionRegistryPort
    ){
        return new UserSseConnection(userId, connectionRegistryPort);
    }

}
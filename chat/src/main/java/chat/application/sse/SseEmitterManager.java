package chat.application.sse;


import chat.application.port.output.SseConnectionRegistryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SseEmitterManager {

    private final SseConnectionRegistryPort<String, UserSseConnection> connectionRegistryPort;

    public UserSseConnection createSseEmitter(String userId) {
        return UserSseConnection.create(userId, connectionRegistryPort);
    }

}

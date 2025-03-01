package chat.application.sse;


import chat.application.port.output.SseConnectionRegistryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SseEmitterManager {

    private final SseConnectionRegistryPort<String, UserSseConnection> connectionPoolPort;

    public UserSseConnection createSseEmitter(String userId) {
        UserSseConnection userSseConnection = UserSseConnection.create(userId, connectionPoolPort);
        connectionPoolPort.saveEmitter(userId, userSseConnection);
       return userSseConnection;
    }

}

package chat.application.sse;


import chat.application.port.output.SseConnectionPoolPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SseEmitterManager {

    private final SseConnectionPoolPort<String, UserSseConnection> connectionPoolPort;

    public UserSseConnection createSseEmitter(String userId) {
        UserSseConnection userSseConnection = UserSseConnection.create(userId, connectionPoolPort);
        connectionPoolPort.addSession(userId, userSseConnection);
       return userSseConnection;
    }

}

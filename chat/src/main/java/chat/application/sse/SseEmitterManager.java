package chat.application.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SseEmitterManager {

    private final SseConnectionStore<String, UserSseConnection> sseConnectionStore;

    public UserSseConnection createSseEmitter(String userId) {
        return UserSseConnection.create(userId, sseConnectionStore);
    }

}
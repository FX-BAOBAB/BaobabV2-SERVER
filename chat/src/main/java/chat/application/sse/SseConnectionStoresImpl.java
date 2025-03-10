package chat.application.sse;

import global.sse.SseConnectionStore;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseConnectionStoresImpl implements SseConnectionStore<String, SseEmitter> {

    private static final Map<String, SseEmitter> connectionStore = new ConcurrentHashMap<>();

    @Override
    public SseEmitter saveEmitter(String userId, SseEmitter sseEmitter) {
        connectionStore.put(userId, sseEmitter);
        return connectionStore.get(userId);
    }

    @Override
    public Optional<SseEmitter> findEmitter(String userId) {
        return Optional.of(connectionStore.get(userId));
    }

    @Override
    public void deleteEmitter(String userId) {
        connectionStore.remove(userId);
    }

}

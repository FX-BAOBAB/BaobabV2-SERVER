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
    public SseEmitter saveEmitter(String uniqueKey, SseEmitter sseEmitter) {
        connectionStore.put(uniqueKey, sseEmitter);
        return connectionStore.get(uniqueKey);
    }

    @Override
    public Optional<SseEmitter> findEmitter(String uniqueKey) {
        return Optional.ofNullable(connectionStore.get(uniqueKey));
    }

    @Override
    public void deleteEmitter(String uniqueKey) {
        connectionStore.remove(uniqueKey);
    }

}

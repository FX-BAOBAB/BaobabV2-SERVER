package global.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public abstract class AbstractSseEmitterManager {

    protected final SseConnectionStore<String, SseEmitter> sseConnectionStore;
    private final Long timeout;

    public AbstractSseEmitterManager(SseConnectionStore<String, SseEmitter> sseConnectionStore,
        Long timeout) {
        this.sseConnectionStore = sseConnectionStore;
        this.timeout = timeout;
    }

    public SseEmitter createEmitter(String userId) {
        SseEmitter sseEmitter = sseConnectionStore.saveEmitter(userId, new SseEmitter(timeout));

        sseEmitter.onCompletion(() -> {
            this.sseConnectionStore.deleteEmitter(userId);
        });

        sseEmitter.onTimeout(sseEmitter::complete);

        sendInitialMessage(sseEmitter);

        return sseEmitter;
    }

    protected abstract void sendInitialMessage(SseEmitter sseEmitter);

}

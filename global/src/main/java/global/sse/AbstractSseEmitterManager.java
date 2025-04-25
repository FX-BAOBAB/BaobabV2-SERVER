package global.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
public abstract class AbstractSseEmitterManager {

    protected final SseConnectionStore<String, SseEmitter> sseConnectionStore;
    private final Long timeout;

    public AbstractSseEmitterManager(SseConnectionStore<String, SseEmitter> sseConnectionStore,
        Long timeout) {
        this.sseConnectionStore = sseConnectionStore;
        this.timeout = timeout;
    }

    public SseEmitter createEmitter(String uniqueKey) {
        SseEmitter sseEmitter = sseConnectionStore.saveEmitter(uniqueKey, new SseEmitter(timeout));

        sseEmitter.onCompletion(() -> {
            log.info("onCompletion");
            this.sseConnectionStore.deleteEmitter(uniqueKey);
        });

        sseEmitter.onTimeout(() -> {
            log.info("onTimeout");
            sseEmitter.complete();
        });

        sseEmitter.onError(throwable -> {
                log.info("onError");
                sseEmitter.complete();
        });

        sendInitialMessage(sseEmitter);

        return sseEmitter;
    }

    protected abstract void sendInitialMessage(SseEmitter sseEmitter);

}

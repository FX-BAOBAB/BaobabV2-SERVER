package chat.application.sse;

import global.sse.SseEventType;
import global.sse.SseMessageManager;
import global.sse.AbstractSseEmitterManager;
import global.sse.SseConnectionStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
public class SseEmitterManager extends AbstractSseEmitterManager {

    private final SseMessageManager sseMessageManager;
    private static final Long DEFAULT_TIMEOUT = 30L * 1000;

    public SseEmitterManager(SseConnectionStore<String, SseEmitter> sseConnectionStore,
        SseMessageManager sseMessageManager) {
        super(sseConnectionStore, DEFAULT_TIMEOUT);
        this.sseMessageManager = sseMessageManager;
    }

    /**
     * SSE 첫 연결 시 클라이언트에게 더미 메시지를 보내야 함
     * 메시지가 없는 경우 재연결 시도 시 503 에러
     * @param sseEmitter
     */
    @Override
    protected void sendInitialMessage(SseEmitter sseEmitter) {
        sseMessageManager.sendMessage(sseEmitter, SseEventType.CHAT, "Chat Connected");
    }

}

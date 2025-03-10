package chat.application;

import chat.application.port.input.ChatConnectionUseCase;
import chat.application.sse.SseEmitterManager;
import global.sse.SseConnectionStore;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatConnectionService implements ChatConnectionUseCase {

    private final SseConnectionStore<String, SseEmitter> sseConnectionStore;
    private final SseEmitterManager sseEmitterManager;
    private final StringRedisTemplate redisTemplate;

    private final ServletWebServerApplicationContext webServerApplicationContext;

    private static final String PROTOCOL = "http://";

    @Override
    public SseEmitter connectChatRoom(String userId) {
        redisTemplate.opsForValue().set(userId, getServerAddress());
        redisTemplate.expire(userId, Duration.ofHours(1));
        return sseConnectionStore.saveEmitter(userId, sseEmitterManager.createEmitter(userId));
    }


    @Override
    public void disconnectChatRoom(String userId) {
        redisTemplate.delete(userId);
        sseConnectionStore.deleteEmitter(userId);
    }

    @Override
    public Optional<SseEmitter> getConnectedUserSse(String userId) {
        return sseConnectionStore.findEmitter(userId);
    }

    @Override
    public String getConnectedServerAddress(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    private String getServerAddress() {
        int port = webServerApplicationContext.getWebServer().getPort();
        try {
            return PROTOCOL + InetAddress.getLocalHost().getHostAddress() + ":" + port;
        } catch (UnknownHostException e) {
            throw new RuntimeException("Failed to get server address", e);
        }
    }

}

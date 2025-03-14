package chat.application;

import chat.application.port.input.ChatConnectionUseCase;
import chat.application.sse.ChatRedisKeyGenerator;
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
    private static final Duration REDIS_TTL = Duration.ofHours(1);

    @Override
    public SseEmitter connectChatRoom(String userId, String chatRoomId) {
        String uniqueKey = ChatRedisKeyGenerator.getUniqueKey(userId, chatRoomId);
        redisTemplate.opsForValue().set(uniqueKey, getServerAddress());
        redisTemplate.expire(uniqueKey, REDIS_TTL);
        return sseConnectionStore.saveEmitter(uniqueKey, sseEmitterManager.createEmitter(uniqueKey));
    }

    @Override
    public void disconnectChatRoom(String userId, String chatRoomId) {
        String uniqueKey = ChatRedisKeyGenerator.getUniqueKey(userId, chatRoomId);
        redisTemplate.delete(uniqueKey);
        sseConnectionStore.deleteEmitter(uniqueKey);
    }

    @Override
    public Optional<SseEmitter> getConnectedUserSse(String userId, String chatRoomId) {
        String uniqueKey = ChatRedisKeyGenerator.getUniqueKey(userId, chatRoomId);
        return sseConnectionStore.findEmitter(uniqueKey);
    }

    @Override
    public Optional<String> getConnectedServerAddress(String userId, String chatRoomId) {
        String uniqueKey = ChatRedisKeyGenerator.getUniqueKey(userId, chatRoomId);
        return Optional.ofNullable(redisTemplate.opsForValue().get(uniqueKey));
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

package chat.application;

import chat.application.port.input.ChatConnectionUseCase;
import chat.application.sse.SseConnectionStore;
import chat.application.sse.SseEmitterManager;
import chat.application.sse.UserSseConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatConnectionService implements ChatConnectionUseCase {

    private final SseConnectionStore<String, UserSseConnection> sseConnectionStore;
    private final SseEmitterManager sseEmitterManager;
    private final StringRedisTemplate redisTemplate;

    private final ServletWebServerApplicationContext webServerApplicationContext;

    private static final String PROTOCOL = "http://";

    @Override
    public UserSseConnection connectChatRoom(String userId) {
        redisTemplate.opsForValue().set(userId, getServerAddress());
        redisTemplate.expire(userId, Duration.ofHours(1));
        return sseConnectionStore.saveEmitter(userId, sseEmitterManager.createSseEmitter(userId));
    }


    @Override
    public void disconnectChatRoom(String userId, UserSseConnection connection) {
        redisTemplate.delete(userId);
        sseConnectionStore.deleteEmitter(connection);
    }

    @Override
    public List<UserSseConnection> getConnectedUserSseList(List<String> userIdList) {
        return sseConnectionStore.findEmitter(userIdList);
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

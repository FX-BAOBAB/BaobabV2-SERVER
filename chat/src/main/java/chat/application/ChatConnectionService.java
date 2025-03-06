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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatConnectionService implements ChatConnectionUseCase {

    private final SseConnectionStore<String, UserSseConnection> sseConnectionStore;
    private final SseEmitterManager sseEmitterManager;
    private final StringRedisTemplate redisTemplate;

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void connectChatRoom(String userId) {
        sseConnectionStore.saveEmitter(userId, sseEmitterManager.createSseEmitter(userId));
        redisTemplate.opsForValue().set(userId, getServerAddress());
        redisTemplate.expire(userId, Duration.ofHours(1));
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
        try {
            return InetAddress.getLocalHost().getHostAddress() + serverPort;
        } catch (UnknownHostException e) {
            throw new RuntimeException("Failed to get server address", e);
        }
    }

}

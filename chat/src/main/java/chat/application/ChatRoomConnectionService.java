package chat.application;

import chat.application.port.input.ChatRoomConnectionUseCase;
import chat.application.port.output.SseConnectionRegistryPort;
import chat.application.sse.SseEmitterManager;
import chat.application.sse.UserSseConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomConnectionService implements ChatRoomConnectionUseCase {

    private final SseConnectionRegistryPort<String, UserSseConnection> sseConnectionRegistryPort;
    private final SseEmitterManager sseEmitterManager;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void connectChatRoom(String userId) {
        try {
            String serverAddress = InetAddress.getLocalHost().getHostAddress();
            redisTemplate.opsForValue().set(userId, serverAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(); // TODO EXCEPTION
        }
        sseConnectionRegistryPort.saveEmitter(userId, sseEmitterManager.createSseEmitter(userId));
    }

    @Override
    public void disconnectChatRoom(String userId, UserSseConnection connection) {
        redisTemplate.delete(userId);
        sseConnectionRegistryPort.deleteEmitter(connection);
    }

}

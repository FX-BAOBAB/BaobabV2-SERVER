package chat.application;

import chat.application.port.input.ChatRoomConnectionUseCase;
import chat.application.port.output.SseConnectionRegistryPort;
import chat.application.sse.SseEmitterManager;
import chat.application.sse.UserSseConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomConnectionService implements ChatRoomConnectionUseCase {

    private final SseConnectionRegistryPort<String, UserSseConnection> sseConnectionRegistryPort;
    private final SseEmitterManager sseEmitterManager;
    private final StringRedisTemplate redisTemplate;

    private final static String CONNECTED_USER = "connectedUsers";

    @Override
    public UserSseConnection connectChatRoom(String userId) {
        redisTemplate.opsForSet().add(CONNECTED_USER, userId);
        return sseEmitterManager.createSseEmitter(userId);
    }

    @Override
    public void disconnectChatRoom(String userId, UserSseConnection connection) {
        redisTemplate.opsForSet().remove(CONNECTED_USER, userId);
        sseConnectionRegistryPort.deleteEmitter(connection);
    }

}

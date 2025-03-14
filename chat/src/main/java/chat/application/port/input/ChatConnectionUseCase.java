package chat.application.port.input;

import java.util.Optional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatConnectionUseCase {

    SseEmitter connectChatRoom(String userId, String chatRoomId);

    void disconnectChatRoom(String userId, String chatRoomId);

    Optional<SseEmitter> getConnectedUserSse(String userId, String chatRoomId);

    Optional<String> getConnectedServerAddress(String userId, String chatRoomId);

}

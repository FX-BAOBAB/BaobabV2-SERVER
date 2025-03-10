package chat.application.port.input;

import java.util.Optional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatConnectionUseCase {

    SseEmitter connectChatRoom(String userId);

    void disconnectChatRoom(String userId);

    Optional<SseEmitter> getConnectedUserSse(String userId);

    String getConnectedServerAddress(String userId);

}

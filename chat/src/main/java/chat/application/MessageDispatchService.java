package chat.application;

import chat.application.port.input.ChatConnectionUseCase;
import chat.application.port.input.MessageDispatchUseCase;
import global.sse.SseEventType;
import global.sse.SseMessageManager;
import chat.domain.ChatMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class MessageDispatchService implements MessageDispatchUseCase {

    private final ChatConnectionUseCase chatConnectionUseCase;
    private final SseMessageManager sseMessageManager;

    @Override
    public void dispatchMessage(ChatMessage chatMessage) {

        List<String> disconnectedUserIdList = new ArrayList<>();

        chatMessage.getReceiverIdList().forEach(receiverId -> {
            Optional<SseEmitter> connectedUserSse = chatConnectionUseCase.getConnectedUserSse(receiverId);

            if (connectedUserSse.isPresent()) { // SSE 가 있는 경우, 메시지 전송
                sseMessageManager.sendMessage(connectedUserSse.get(), SseEventType.CHAT, chatMessage);
            } else {
                // 연결되지 않은 사용자는 FCM 리스트에 추가
                disconnectedUserIdList.add(receiverId);
            }
        });

        if (!disconnectedUserIdList.isEmpty()) {
            // FCM 전송
        }

    }

}

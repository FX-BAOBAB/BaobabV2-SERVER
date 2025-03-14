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

        // Memory 에 존재하는 SSE 조회 및 전송, 미조회 유저는 FCM 으로 전송
        chatMessage.getReceiverIdList().forEach(receiverId -> {
            Optional<SseEmitter> connectedUserSse = chatConnectionUseCase.getConnectedUserSse(
                receiverId, chatMessage.getChatRoomId());

            connectedUserSse.ifPresentOrElse(sseEmitter ->
                    sseMessageManager.sendMessage(sseEmitter, SseEventType.CHAT, chatMessage),
                () ->
                    disconnectedUserIdList.add(receiverId)
            );

        });

        if (!disconnectedUserIdList.isEmpty()) {
            // TODO FCM 전송
        }

    }

}

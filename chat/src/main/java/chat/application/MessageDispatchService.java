package chat.application;

import chat.application.port.input.ChatConnectionUseCase;
import chat.application.port.input.MessageDispatchUseCase;
import chat.application.sse.SseMessageManager;
import chat.application.sse.UserSseConnection;
import chat.domain.ChatMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageDispatchService implements MessageDispatchUseCase {

    private final ChatConnectionUseCase chatConnectionUseCase;
    private final SseMessageManager sseMessageManager;

    @Override
    public void dispatchMessage(ChatMessage chatMessage) {

        List<UserSseConnection> connectedUserSseList = chatConnectionUseCase.getConnectedUserSseList(
            chatMessage.getReceiverIdList());

        // SSE 에 연결된 userId 리스트 추출
        List<String> connectedUserIdList = connectedUserSseList.stream()
            .map(UserSseConnection::getUserId)
            .toList();

        // SSE 로 메시지 전송 - message 수정 필요
        connectedUserSseList.forEach(
            UserSse -> sseMessageManager.sendMessage(UserSse, chatMessage));

        // SSE 에 연결되지 않은 userId 리스트 추출
        List<String> disconnectedUserIdList = chatMessage.getReceiverIdList().stream()
            .filter(userId -> !connectedUserIdList.contains(userId))
            .toList();

        // FCM 전송

    }

}

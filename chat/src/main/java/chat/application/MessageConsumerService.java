package chat.application;

import chat.adapter.output.client.ChatClient;
import chat.application.port.input.MessageConsumerUseCase;
import chat.domain.ChatMessage;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageConsumerService implements MessageConsumerUseCase {

    private final ChatConnectionService chatConnectionService;
    private final ChatClient chatClient;

    @Override
    public void consumeMessage(ChatMessage chatMessage) {

        List<String> receiverIdList = chatMessage.getReceiverIdList();

        // IP : userIdList 로 그룹화
        Map<String, List<String>> serverGroup = groupReceiverByServer(receiverIdList);

        // 서버 그룹별 receiverId 지정 후 전송
        sendMessageToServer(serverGroup, chatMessage);

    }

    private Map<String, List<String>> groupReceiverByServer(List<String> receiverIdList) {
        Map<String, List<String>> serverGroup = new HashMap<>();
        receiverIdList.forEach(receiverId -> {
            String serverAddress = chatConnectionService.getConnectedServerAddress(receiverId);
            // TODO Server Address 가 Null 인 경우 (접속하지 않은 경우) -> FCM 전송 처리
            serverGroup.computeIfAbsent(serverAddress, key -> new ArrayList<>()).add(receiverId);
        });
        return serverGroup;
    }

    private void sendMessageToServer(Map<String, List<String>> serverGroup, ChatMessage chatMessage) {
        serverGroup.forEach((serverAddress, userIdList) -> {
            ChatMessage targetMessage = chatMessage.toBuilder()
                .receiverIdList(userIdList)
                .build();

            URI targetServer = URI.create(serverAddress);
            chatClient.sendMessage(targetServer, targetMessage);
        });
    }

}

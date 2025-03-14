package chat.application;

import chat.adapter.output.client.ChatClient;
import chat.application.port.input.MessageConsumerUseCase;
import chat.domain.ChatMessage;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        Map<String, List<String>> serverGroup = groupReceiverByServer(receiverIdList,
            chatMessage.getChatRoomId());

        // 서버 그룹별 receiverId 지정 후 전송
        sendMessageToServer(serverGroup, chatMessage);

    }

    private Map<String, List<String>> groupReceiverByServer(List<String> receiverIdList,
        String chatRoomId) {

        Map<String, List<String>> serverGroup = new HashMap<>();
        List<String> disconnectedUserIdList = new ArrayList<>(); // REDIS 미 조회 사용자 List

        receiverIdList.forEach(receiverId -> {
            Optional<String> connectedServerAddress = chatConnectionService.getConnectedServerAddress(
                receiverId, chatRoomId); // Redis 에서 접속한 사용자 조회

            connectedServerAddress.ifPresentOrElse(serverAddress ->
                    serverGroup.computeIfAbsent(serverAddress, key -> new ArrayList<>()).add(receiverId),
                () ->
                    disconnectedUserIdList.add(receiverId) // 서버에 연결되지 않은 경우 list 추가
            );

        });

        if (!disconnectedUserIdList.isEmpty()) {
            // TODO FCM 전송
        }
        return serverGroup;
    }

    private void sendMessageToServer(Map<String, List<String>> serverGroup,
        ChatMessage chatMessage) {
        serverGroup.forEach((serverAddress, userIdList) -> {
            ChatMessage targetMessage = chatMessage.toBuilder()
                .receiverIdList(userIdList)
                .build();

            URI targetServer = URI.create(serverAddress);
            chatClient.sendMessage(targetServer, targetMessage);
        });
    }

}

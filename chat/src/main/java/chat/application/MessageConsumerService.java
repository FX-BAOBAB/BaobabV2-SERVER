package chat.application;

import chat.adapter.output.client.ChatClient;
import chat.adapter.output.client.MessageClient;
import chat.adapter.output.client.dto.MulticastMessageRequest;
import chat.application.port.input.MessageConsumerUseCase;
import chat.domain.ChatMessage;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageConsumerService implements MessageConsumerUseCase {

    private final ChatConnectionService chatConnectionService;
    private final ChatClient chatClient;
    private final MessageClient messageClient;

    @Override
    public void consumeMessage(ChatMessage chatMessage) {
        // 서버별 그룹화
        Map<String, List<String>> serverGroup = new HashMap<>();
        List<String> disconnectedUserIdList = new ArrayList<>();

        for (String receiverId : chatMessage.getReceiverIdList()) {
            Optional<String> serverAddress = chatConnectionService.getConnectedServerAddress(
                receiverId, chatMessage.getChatRoomId());

            if (serverAddress.isPresent()) {
                serverGroup.computeIfAbsent(serverAddress.get(), key -> new ArrayList<>())
                    .add(receiverId);
            } else {
                disconnectedUserIdList.add(receiverId);
            }
        }

        // 서버 그룹으로 메시지 전송
        sendMessageToConnectedServers(serverGroup, chatMessage);

        // 연결되지 않은 사용자에게 FCM 전송
        if (!disconnectedUserIdList.isEmpty()) {
            sendFcmToDisconnectedUsers(disconnectedUserIdList, chatMessage);
        }
    }

    private void sendMessageToConnectedServers(Map<String, List<String>> serverGroup,
        ChatMessage chatMessage) {
        serverGroup.forEach((serverAddress, userIdList) -> {
            ChatMessage targetMessage = chatMessage.toBuilder()
                .receiverIdList(userIdList)
                .build();

            URI targetServer = URI.create(serverAddress);
            chatClient.sendMessage(targetServer, targetMessage);
        });
    }

    private void sendFcmToDisconnectedUsers(List<String> disconnectedUserIdList,
        ChatMessage chatMessage) {
        messageClient.sendMultipleMessages(
            new MulticastMessageRequest(chatMessage.getNickname() + "님의 메시지",
                chatMessage.getMessage(), disconnectedUserIdList));
        log.info("FCM 전송");
    }
}

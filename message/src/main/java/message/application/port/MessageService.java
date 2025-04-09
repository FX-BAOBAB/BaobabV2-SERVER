package message.application.port;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import global.errorcode.ErrorCode;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.adapter.output.client.TokenClient;
import message.application.port.input.SendMessageUseCase;
import message.application.port.output.SendMessagePort;
import message.core.common.error.MessageErrorCode;
import message.core.common.exception.message.FailedToSendMessageException;
import message.domain.command.MessageCommand;
import message.domain.command.MulticastMessageCommand;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService implements SendMessageUseCase {

    private final SendMessagePort sendMessagePort;

    private final TokenClient tokenClient;

    /**
     * Sends a message to a single user using the provided command.
     * @param command the message command containing user ID, title, and body
     */
    @Override
    public void send(MessageCommand command) {
        String findToken = tokenClient.getFcmToken(command.getUserId());
        try {
            sendMessage(createMessage(command.getTitle(), command.getBody(), findToken));
        } catch (FirebaseMessagingException e) {
            throw new FailedToSendMessageException(MessageErrorCode.FAILED_TO_SEND_MESSAGE);
        }
    }

    /**
     * Sends a multicast message to multiple users using the provided command.
     * @param command the multicast message command containing user IDs, title, and body
     */
    @Override
    public void send(MulticastMessageCommand command) {
        List<String> findTokens = tokenClient.getFcmTokens(command.getUserIds());
        try {
            for (String token : findTokens) {
                Message message = createMessage(command.getTitle(), command.getBody(), token);
                sendMessage(message);
            }
        } catch (FirebaseMessagingException e) {
            throw new FailedToSendMessageException(MessageErrorCode.FAILED_TO_SEND_MESSAGE);
        }
    }

    @Async
    void sendMessage(Message message)
        throws FirebaseMessagingException {
        try {
            sendMessagePort.send(message);
        } catch (ExecutionException e) {
            throw new FailedToSendMessageException(MessageErrorCode.FAILED_TO_SEND_MESSAGE,
                e.getCause().getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new FailedToSendMessageException(ErrorCode.ASYNC_ERROR, e.getMessage());
        }
    }

    private Message createMessage(String title, String body, String token) {
        Notification notification = createNotification(title, body);
        return Message.builder()
            .setNotification(notification)
            .setToken(token)
            .setWebpushConfig(buildWebPushPayload())
            .setApnsConfig(buildApnsPayload())
            .build();
    }

    private Notification createNotification(String title, String body) {
        return Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build();
    }

    // iOS 설정
    private ApnsConfig buildApnsPayload() {
        return ApnsConfig.builder()
            .putHeader("apns-priority", "10")  // APNs 우선순위를 10으로 설정 (최고 우선순위)
            .putHeader("apns-push-type", "alert")  // APNs 푸시 유형을 알림으로 설정
            .setAps(Aps.builder().build())
            .build();
    }

    // Web 설정
    private WebpushConfig buildWebPushPayload() {
        return WebpushConfig.builder()
            .putHeader("Urgency", "high") // 알림 우선순위 설정
            .build();
    }

}

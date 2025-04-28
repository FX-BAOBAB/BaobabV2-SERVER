package message.application;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import global.errorcode.ErrorCode;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.application.port.input.SendMessageUseCase;
import message.application.port.output.FcmTokenPersistencePort;
import message.application.port.output.SendMessagePort;
import message.core.common.error.MessageErrorCode;
import message.core.common.exception.message.FailedToSendMessageException;
import message.domain.command.MessageCommand;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService implements SendMessageUseCase {

    private final SendMessagePort sendMessagePort;

    private final FcmTokenPersistencePort fcmTokenPersistencePort;

    /**
     * Sends messages using provided command.
     * @param command the message command containing user IDs, title, and body
     */
    @Override
    public void send(MessageCommand command) {
        List<String> fcmTokens = fcmTokenPersistencePort.findByUserIds(command.getUserIds());

        if (fcmTokens != null && !fcmTokens.isEmpty()) {
            createMessages(command, fcmTokens).forEach(this::sendMessage);
        }
    }

    @Async
    void sendMessage(Message message) {
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

    private List<Message> createMessages(MessageCommand command, List<String> tokens) {
        Notification notification = createNotification(command);
        return tokens.stream()
            .map(token -> Message.builder()
                .setNotification(notification)
                .setToken(token)
                .setWebpushConfig(buildWebPushPayload())
                .setApnsConfig(buildApnsPayload())
                .build())
            .toList();
    }

    private Notification createNotification(MessageCommand command) {
        return Notification.builder()
            .setTitle(command.getTitle())
            .setBody(command.getBody())
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

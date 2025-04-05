package message.adapter.output.message;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import global.errorcode.ErrorCode;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.application.port.output.SendMessagePort;
import message.core.common.error.MessageErrorCode;
import message.core.common.exception.message.FailedToSendMessageException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmMessageSender implements SendMessagePort {

    private final FirebaseMessaging  firebaseMessaging;

    private final Executor messageCallbackExecutor;

    @Override
    public void send(Message message) {
        ApiFuture<String> apiFuture = firebaseMessaging.sendAsync(message);
        apiFuture.addListener(() -> {
            try {
                apiFuture.get();
            } catch (ExecutionException e) {
                throw new FailedToSendMessageException(MessageErrorCode.FAILED_TO_SEND_MESSAGE,
                    e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Interrupted during message send: {}", e.getMessage());
                throw new FailedToSendMessageException(ErrorCode.ASYNC_ERROR, e.getMessage());
            }
        }, messageCallbackExecutor);
    }

}
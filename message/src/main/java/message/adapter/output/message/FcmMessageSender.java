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

    private static final int MAX_ATTEMPTS = 5;

    private static final int INITIAL_DELAY_MS = 10000;

    private static final int DELAY_MULTIPLIER = 2;

    @Override
    public void send(Message message) {
        ApiFuture<String> apiFuture = firebaseMessaging.sendAsync(message);
        apiFuture.addListener(() -> {
            try {
                apiFuture.get();
            } catch (ExecutionException e) {
                if (shouldRetry(e)) {
                    retrySendWithBackoff(message);
                }
                throw new FailedToSendMessageException(MessageErrorCode.FAILED_TO_SEND_MESSAGE,
                    e.getCause().getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Interrupted during message send: {}", e.getMessage());
                throw new FailedToSendMessageException(ErrorCode.ASYNC_ERROR, e.getMessage());
            }
        }, messageCallbackExecutor);
    }

    private void retrySendWithBackoff(Message message) {
        int retryAttempt = 0;

        while (retryAttempt < MAX_ATTEMPTS) {
            int delay = calculateRetryDelay(retryAttempt);

            sleep(delay);

            if (resend(message)) {
                break;
            }
            retryAttempt++;
        }
    }

    private boolean resend(Message message) {
        try {
            String response = firebaseMessaging.send(message);
            return response != null;
        } catch (FirebaseMessagingException e) {
            log.error("Failed to resend message: {}", e.getMessage());
            return false;
        }
    }

    private boolean shouldRetry(Exception e) {
        if (e.getCause() instanceof FirebaseMessagingException fme) {
            MessagingErrorCode errorCode = fme.getMessagingErrorCode();
            return isRetryErrorCode(errorCode);
        }
        return false;
    }

    private boolean isRetryErrorCode(MessagingErrorCode errorCode) {
        log.info("Error code: {}", errorCode);
        return errorCode == MessagingErrorCode.INTERNAL ||
               errorCode == MessagingErrorCode.UNAVAILABLE;
    }

    private int calculateRetryDelay(int retryAttempt) {
        double exponentialDelay = INITIAL_DELAY_MS * Math.pow(DELAY_MULTIPLIER, retryAttempt);
        double randomJitter = Math.random() * INITIAL_DELAY_MS;
        return (int) (exponentialDelay + randomJitter);
    }

    private void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}

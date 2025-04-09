package token.application;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import token.adapter.input.web.response.SubscriptionResponse;
import token.application.port.input.DefaultFcmTokenUseCase;
import token.application.port.input.SubscribeTopicUseCase;
import token.application.port.output.FcmTokenPersistencePort;
import token.application.port.output.SubscriptionPort;
import token.core.common.error.FcmTokenErrorCode;
import token.core.common.exception.topic.FailedToSubscribeTopicException;
import token.domain.command.FcmTokenSaveCommand;
import token.domain.command.TopicSubscriptionCommand;
import token.domain.dto.FcmTokenSaveForm;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmTokenService implements DefaultFcmTokenUseCase, SubscribeTopicUseCase {

    private final FcmTokenPersistencePort fcmTokenPersistencePort;

    private final SubscriptionPort subscriptionPort;

    private static final int TOKEN_EXPIRATION_DAYS = 270;

    @Override
    public boolean saveFcmToken(FcmTokenSaveCommand command) {
        return fcmTokenPersistencePort.saveFcmToken(FcmTokenSaveForm.of(command));
    }

    @Override
    public List<String> getFcmTokens(List<String> userIds) {
        return fcmTokenPersistencePort.findByUserIds(userIds);
    }

    @Override
    public void deleteFcmToken(String token) {
        fcmTokenPersistencePort.deleteFcmToken(token);
    }

    @Override
    public SubscriptionResponse subscribeToTopic(TopicSubscriptionCommand command) {
        try {
            boolean isSuccess = subscriptionPort.subscribeToTopic(List.of(command.getToken()),
                command.getTopic());
            return SubscriptionResponse.of(command.getTopic(), isSuccess);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to subscribe to {}: {},", command.getTopic(), e.getMessage());
            throw new FailedToSubscribeTopicException(FcmTokenErrorCode.FAILED_TO_SUBSCRIBE_TOPIC);
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredTokens() {
        LocalDate threshold = LocalDate.now().minusDays(TOKEN_EXPIRATION_DAYS);
        fcmTokenPersistencePort.deleteTokensUpTo(threshold);
    }

}

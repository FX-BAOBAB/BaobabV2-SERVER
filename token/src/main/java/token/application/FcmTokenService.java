package token.application;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import token.adapter.input.web.response.SubscriptionResponse;
import token.adapter.output.persistence.enums.DeviceType;
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

    private static final int WEB_FCM_TOKEN_EXPIRATION_DAYS = 2;

    private static final int IOS_FCM_TOKEN_EXPIRATION_DAYS = 270;

    @Transactional
    @Override
    public boolean saveFcmToken(FcmTokenSaveCommand command) {
        return fcmTokenPersistencePort.saveFcmToken(FcmTokenSaveForm.of(command));
    }

    @Override
    public List<String> getFcmTokens(List<String> userIds) {
        return fcmTokenPersistencePort.findByUserIds(userIds);
    }

    @Transactional
    @Override
    public boolean deleteFcmToken(String token) {
        return fcmTokenPersistencePort.deleteFcmToken(token);
    }

    @Override
    public SubscriptionResponse subscribeToTopic(TopicSubscriptionCommand command) {
        try {
            boolean isSuccess = subscriptionPort.subscribeToTopic(List.of(command.getToken()),
                command.getTopic());
            return SubscriptionResponse.of(command.getTopic(), isSuccess);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to subscribe to {}: {}", command.getTopic(), e.getMessage());
            throw new FailedToSubscribeTopicException(FcmTokenErrorCode.FAILED_TO_SUBSCRIBE_TOPIC);
        }
    }

    /**
     * Scheduled task that deletes expired FCM (Firebase Cloud Messaging) tokens.
     * This method runs daily at midnight (00:00:00) based on the cron schedule.
     * It removes tokens that exceed their expiration period:
     * 2 days for WEB devices and 270 days for iOS devices.
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    void deleteExpiredTokens() {
        LocalDate now = LocalDate.now();
        LocalDate webThreshold = now.minusDays(WEB_FCM_TOKEN_EXPIRATION_DAYS); // WEB: 2일 전 날짜 계산
        LocalDate iosThreshold = now.minusDays(IOS_FCM_TOKEN_EXPIRATION_DAYS); // IOS: 270일 전 날짜 계산

        fcmTokenPersistencePort.deleteTokensUpTo(DeviceType.WEB, webThreshold); // WEB 기기에서 2일 이상 경과한 토큰 삭제
        fcmTokenPersistencePort.deleteTokensUpTo(DeviceType.iOS, iosThreshold); // IOS 기기에서 270일 이상 경과한 토큰 삭제
    }

}

package message.application;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.adapter.input.web.response.SubscriptionResponse;
import message.adapter.output.persistence.enums.DeviceType;
import message.application.port.input.DefaultFcmTokenUseCase;
import message.application.port.input.SubscribeTopicUseCase;
import message.application.port.output.FcmTokenPersistencePort;
import message.application.port.output.SubscriptionPort;
import message.core.common.error.MessageErrorCode;
import message.core.common.exception.topic.FailedToSubscribeTopicException;
import message.domain.command.FcmTokenSaveCommand;
import message.domain.command.TopicSubscriptionCommand;
import message.domain.dto.FcmTokenSaveForm;
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
            throw new FailedToSubscribeTopicException(MessageErrorCode.FAILED_TO_SUBSCRIBE_TOPIC);
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
        log.info("Deleting expired FCM tokens...");

        LocalDate now = LocalDate.now();
        LocalDate webThreshold = now.minusDays(WEB_FCM_TOKEN_EXPIRATION_DAYS); // WEB: 2일 전 날짜 계산
        LocalDate iosThreshold = now.minusDays(IOS_FCM_TOKEN_EXPIRATION_DAYS); // IOS: 270일 전 날짜 계산

        fcmTokenPersistencePort.deleteTokensUpTo(DeviceType.WEB, webThreshold); // WEB 기기에서 2일 이상 경과한 토큰 삭제
        fcmTokenPersistencePort.deleteTokensUpTo(DeviceType.iOS, iosThreshold); // IOS 기기에서 270일 이상 경과한 토큰 삭제
    }

}

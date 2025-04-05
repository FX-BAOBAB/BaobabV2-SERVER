package token.application;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import token.adapter.input.web.response.SubscriptionResponse;
import token.application.port.input.GetFcmTokenUseCase;
import token.application.port.input.SaveFcmTokenUseCase;
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
public class FcmTokenService implements SaveFcmTokenUseCase{

    private final FcmTokenPersistencePort fcmTokenPersistencePort;

    @Override
    public boolean saveFcmToken(FcmTokenSaveCommand command) {
        return fcmTokenPersistencePort.saveFcmToken(FcmTokenSaveForm.of(command));
    }

}

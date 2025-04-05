package token.application.port.output;

import java.time.LocalDate;
import java.util.List;
import token.domain.dto.FcmTokenSaveForm;

/**
 * DeviceToken Persistence OUTPUT Port
 */
public interface FcmTokenPersistencePort {

    boolean saveFcmToken(FcmTokenSaveForm fcmTokenSaveForm);

    void deleteTokensUpTo(LocalDate threshold);

    String findByUserId(String userId);

    List<String> findByUserIds(List<String> userIds);
}

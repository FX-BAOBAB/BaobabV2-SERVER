package token.application.port.output;

import java.time.LocalDate;
import java.util.List;
import token.domain.dto.FcmTokenSaveForm;

/**
 * DeviceToken Persistence OUTPUT Port
 */
public interface FcmTokenPersistencePort {

    /**
     * Save FCM Token
     *
     * @param fcmTokenSaveForm FCM Token Save Form
     * @return true if the token was saved successfully, false otherwise
     */
    boolean saveFcmToken(FcmTokenSaveForm fcmTokenSaveForm);

    /**
     * Delete FCM Tokens older than the specified threshold date
     *
     * @param threshold The date threshold for deletion
     */
    void deleteTokensUpTo(LocalDate threshold);

    /**
     * Find FCM Tokens by User IDs
     *
     * @param userIds The list of User IDs
     * @return list of FCM tokens linked to the users
     */
    List<String> findByUserIds(List<String> userIds);
}

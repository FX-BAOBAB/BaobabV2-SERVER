package token.application.port.output;

import java.time.LocalDate;
import java.util.List;
import token.adapter.output.persistence.enums.DeviceType;
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
     * Find FCM Tokens by User IDs
     *
     * @param userIds The list of User IDs
     * @return list of FCM tokens linked to the users
     */
    List<String> findByUserIds(List<String> userIds);

    /**
     * Delete FCM Token
     *
     * @param token The FCM Token to delete
     */
    boolean deleteFcmToken(String token);

    /**
     * Delete FCM Tokens older than the specified threshold date
     *
     * @param deviceType The type of device
     * @param threshold The date threshold for deletion
     */
    void deleteTokensUpTo(DeviceType deviceType, LocalDate threshold);

}

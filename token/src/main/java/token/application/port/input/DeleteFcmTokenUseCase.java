package token.application.port.input;

/**
 * Delete FCM Token Input Port
 */
public interface DeleteFcmTokenUseCase {

    /**
     * Delete FCM Token
     *
     * @param token FCM Token
     */
    void deleteFcmToken(String token);

}

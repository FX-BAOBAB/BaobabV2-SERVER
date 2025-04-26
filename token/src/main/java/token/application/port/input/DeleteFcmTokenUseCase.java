package token.application.port.input;

/**
 * Delete FCM Token Input Port
 */
public interface DeleteFcmTokenUseCase {

    /**
     * Delete FCM Token
     *
     * @param token FCM Token
     * return true if the token was deleted successfully, false otherwise
     */
    boolean deleteFcmToken(String token);

}

package token.application.port.input;

import java.util.List;

/**
 * Get FCM Token Input Port
 */
public interface GetFcmTokenUseCase {

    /**
     * Get FCM Tokens By User IDs
     *
     * @param userIds List of User IDs
     * @return List of FCM Tokens
     */
    List<String> getFcmTokens(List<String> userIds);

}

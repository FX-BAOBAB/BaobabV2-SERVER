package message.application.port.input;

import message.domain.command.FcmTokenDeleteCommand;

/**
 * Delete FCM Token Input Port
 */
public interface DeleteFcmTokenUseCase {

    /**
     * Delete FCM Token
     *
     * @param command FCM Token Delete Command
     * return true if the token was deleted successfully, false otherwise
     */
    boolean deleteFcmToken(FcmTokenDeleteCommand command);

}

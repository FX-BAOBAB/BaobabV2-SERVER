package message.application.port.input;

import message.domain.command.FcmTokenSaveCommand;

/**
 * Save FCM Token Input Port
 */
public interface SaveFcmTokenUseCase {

    /**
     * Save FCM Token
     *
     * @param fcmTokenSaveCommand FCM Token Save Command
     * @return true if the token was saved successfully, false otherwise
     */
    boolean saveFcmToken(FcmTokenSaveCommand fcmTokenSaveCommand);

}

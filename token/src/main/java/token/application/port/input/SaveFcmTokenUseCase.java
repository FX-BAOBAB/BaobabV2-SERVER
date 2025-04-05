package token.application.port.input;

import token.domain.command.FcmTokenSaveCommand;

public interface SaveFcmTokenUseCase {

    boolean saveFcmToken(FcmTokenSaveCommand fcmTokenSaveCommand);

}

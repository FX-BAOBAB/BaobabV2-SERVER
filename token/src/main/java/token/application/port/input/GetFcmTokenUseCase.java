package token.application.port.input;

import java.util.List;

public interface GetFcmTokenUseCase {

    String getFcmToken(String userId);

    List<String> getFcmTokens(List<String> userIds);

}

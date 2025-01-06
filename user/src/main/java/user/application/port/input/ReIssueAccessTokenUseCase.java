package user.application.port.input;

import user.security.jwt.model.TokenDto;

public interface ReIssueAccessTokenUseCase {

    TokenDto reIssueAccessToken(String refreshToken);

}

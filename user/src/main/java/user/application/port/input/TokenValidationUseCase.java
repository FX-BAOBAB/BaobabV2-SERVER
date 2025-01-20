package user.application.port.input;

import user.security.jwt.model.JwtInfoDto;

public interface TokenValidationUseCase {

    JwtInfoDto  validateToken(String accessToken);

}

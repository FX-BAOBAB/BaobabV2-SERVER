package user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.application.port.input.TokenValidationUseCase;
import user.security.jwt.model.JwtInfoDto;
import user.security.jwt.service.TokenHelperService;

@Service
@RequiredArgsConstructor
public class TokenValidationService implements TokenValidationUseCase {

    private final TokenHelperService tokenHelperService;

    @Override
    public JwtInfoDto validateToken(String accessToken) {
        JwtInfoDto jwtInfoDto = null;
        if(accessToken != null && accessToken.startsWith("Bearer ")) {
            String token = accessToken.substring(7);
            jwtInfoDto = tokenHelperService.validationToken(token);
        }
        return jwtInfoDto;
    }

}

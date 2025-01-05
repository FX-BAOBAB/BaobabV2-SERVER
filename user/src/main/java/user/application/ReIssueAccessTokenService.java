package user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.application.port.input.ReIssueAccessTokenUseCase;
import user.security.jwt.model.TokenDto;
import user.security.jwt.service.TokenIssueService;

@Service
@RequiredArgsConstructor
public class ReIssueAccessTokenService implements ReIssueAccessTokenUseCase {

    private final TokenIssueService tokenIssueService;

    @Override
    public TokenDto reIssueAccessToken(String refreshToken) {
        return tokenIssueService.reIssueAccessToken(refreshToken);
    }

}

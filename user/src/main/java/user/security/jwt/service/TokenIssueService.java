package user.security.jwt.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.core.common.converter.TokenConverter;
import user.domain.command.TokenCommand;
import user.security.jwt.model.TokenDto;

@Service
@RequiredArgsConstructor
public class TokenIssueService {

    private final TokenHelperService tokenHelperService;
    private final TokenConverter tokenConverter;

    public TokenCommand issueToken(String userId) {
        return Optional.ofNullable(userId).map(id -> {

            TokenDto accessToken = tokenHelperService.issueAccessToken(id);
            TokenDto refreshToken = tokenHelperService.issueRefreshToken(id);

            tokenHelperService.deleteRefreshToken(id);
            tokenHelperService.saveRefreshToken(id, refreshToken.getToken());

            return tokenConverter.toTokenCommand(accessToken, refreshToken);

        }).orElseThrow(() -> new RuntimeException("userId가 null입니다.")); // TODO 적절한 예외 처리
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        if(refreshToken != null && refreshToken.startsWith("Bearer ")) {
            String token = refreshToken.substring(7);
            return tokenHelperService.reIssueAccessToken(token);
        }
        throw new RuntimeException("토큰 에러"); // TODO 예외처리
    }


}

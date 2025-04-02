package user.security.jwt.service;

import global.errorcode.ErrorCode;
import global.user.UserRole;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.core.common.error.TokenErrorCode;
import user.core.common.exception.token.TokenException;
import user.core.common.exception.token.TokenSignatureException;
import user.domain.command.TokenCommand;
import user.security.jwt.model.TokenDto;

@Service
@RequiredArgsConstructor
public class TokenIssueService {

    private final TokenHelperService tokenHelperService;

    public TokenCommand issueToken(String userId, UserRole userRole) {
        return Optional.ofNullable(userId).map(id -> {

            TokenDto accessToken = tokenHelperService.issueAccessToken(id, userRole);
            TokenDto refreshToken = tokenHelperService.issueRefreshToken(id, userRole);

            tokenHelperService.saveRefreshToken(refreshToken.getToken());

            return TokenCommand.of(accessToken, refreshToken);

        }).orElseThrow(() -> new TokenException(ErrorCode.NULL_POINT));
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        if(refreshToken != null && refreshToken.startsWith("Bearer ")) {
            String token = refreshToken.substring(7);
            return tokenHelperService.reIssueAccessToken(token);
        }
        throw new TokenSignatureException(TokenErrorCode.INVALID_TOKEN);
    }

}
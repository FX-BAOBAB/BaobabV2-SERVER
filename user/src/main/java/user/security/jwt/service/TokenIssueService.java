package user.security.jwt.service;

import global.errorcode.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.core.common.converter.TokenConverter;
import user.core.common.error.TokenErrorCode;
import user.core.common.exception.token.TokenException;
import user.core.common.exception.token.TokenSignatureException;
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

package user.domain.command;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import user.security.jwt.model.TokenDto;

@Data
@Builder
public class TokenCommand {

    private String accessToken;

    private LocalDateTime accessTokenExpiredAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiredAt;

    public static TokenCommand of(TokenDto accessToken, TokenDto refreshToken) {
        return TokenCommand.builder()
            .accessToken(accessToken.getToken())
            .accessTokenExpiredAt(accessToken.getExpiredAt())
            .refreshToken(refreshToken.getToken())
            .refreshTokenExpiredAt(refreshToken.getExpiredAt())
            .build();
    }

}

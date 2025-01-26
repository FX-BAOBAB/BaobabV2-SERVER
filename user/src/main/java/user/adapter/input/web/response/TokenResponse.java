package user.adapter.input.web.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import user.domain.command.TokenCommand;

@Data
@Builder
public class TokenResponse {

    private String accessToken;

    private LocalDateTime accessTokenExpiredAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiredAt;

    public static TokenResponse toResponse(TokenCommand tokenCommand) {
        return TokenResponse.builder()
            .accessToken(tokenCommand.getAccessToken())
            .accessTokenExpiredAt(tokenCommand.getAccessTokenExpiredAt())
            .refreshToken(tokenCommand.getRefreshToken())
            .refreshTokenExpiredAt(tokenCommand.getRefreshTokenExpiredAt())
            .build();
    }

}

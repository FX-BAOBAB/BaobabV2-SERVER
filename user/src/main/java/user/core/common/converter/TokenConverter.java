package user.core.common.converter;

import global.annotation.Converter;
import user.adapter.input.web.request.UserLoginRequest;
import user.adapter.input.web.response.TokenResponse;
import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;
import user.security.jwt.model.TokenDto;

@Converter
public class TokenConverter {

    public TokenCommand toTokenCommand(TokenDto accessToken, TokenDto refreshToken) {
        return TokenCommand.builder()
            .accessToken(accessToken.getToken())
            .accessTokenExpiredAt(accessToken.getExpiredAt())
            .refreshToken(refreshToken.getToken())
            .refreshTokenExpiredAt(refreshToken.getExpiredAt())
            .build();
    }

    public UserLoginCommand toLoginCommand(UserLoginRequest userLoginRequest) {
        return UserLoginCommand.builder()
            .email(userLoginRequest.getEmail())
            .password(userLoginRequest.getPassword())
            .build();
    }

    public TokenResponse toTokenResponse(TokenCommand tokenCommand) {
        return TokenResponse.builder()
            .accessToken(tokenCommand.getAccessToken())
            .accessTokenExpiredAt(tokenCommand.getAccessTokenExpiredAt())
            .refreshToken(tokenCommand.getRefreshToken())
            .refreshTokenExpiredAt(tokenCommand.getRefreshTokenExpiredAt())
            .build();
    }

}

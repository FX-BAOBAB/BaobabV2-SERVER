package user.security.jwt.service;

import global.errorcode.ErrorCode;
import global.user.UserRole;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.core.common.error.TokenErrorCode;
import user.core.common.exception.token.TokenException;
import user.security.jwt.ifs.TokenHelperIfs;
import user.security.jwt.model.TokenDto;
import user.security.jwt.model.TokenInfoDto;

@Service
@RequiredArgsConstructor
public class TokenHelperService {

    private final String REFRESH_TOKEN = "refreshToken";
    private final String USER_ID = "userId";
    private final String USER_ROLE = "userRole";

    @Value("${jwt.refresh-token.plus-hour}")
    private int refreshTokenPlusHour;

    private final TokenHelperIfs tokenHelperIfs;
    private final HttpSession httpSession;

    public TokenDto issueAccessToken(String userId, UserRole userRole) {
        Map<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        data.put(USER_ROLE, userRole);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(String userId, UserRole userRole) {
        Map<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        data.put(USER_ROLE, userRole);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        TokenInfoDto tokenInfoDto = this.validationToken(refreshToken);

        String storedToken = (String) httpSession.getAttribute(REFRESH_TOKEN);
        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
        }

        return issueAccessToken(tokenInfoDto.getUserId(), tokenInfoDto.getUserRole());
    }

    public TokenInfoDto validationToken(String token) {
        Map<String, Object> userData = tokenHelperIfs.validationTokenWithThrow(token);

        Object userId = userData.get(USER_ID);
        String userRoleStr = (String) userData.get(USER_ROLE);

        Objects.requireNonNull(userId, () -> {
            throw new TokenException(ErrorCode.NULL_POINT);
        });

        UserRole userRole = UserRole.valueOf(userRoleStr);

        return new TokenInfoDto(userId.toString(), userRole);
    }

    public void saveRefreshToken(String refreshToken) {
        int expirationInSeconds = refreshTokenPlusHour * 60 * 60; // 시간을 초 단위로 변환

        httpSession.setAttribute(REFRESH_TOKEN, refreshToken);
        httpSession.setMaxInactiveInterval(expirationInSeconds); // 세션 만료 시간 설정
    }

}
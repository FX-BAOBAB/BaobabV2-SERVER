package user.security.jwt.service;

import global.errorcode.ErrorCode;
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

@Service
@RequiredArgsConstructor
public class TokenHelperService {

    private final String REFRESH_TOKEN = "refreshToken:";
    private final String USER_ID = "userId";

    @Value("${jwt.refresh-token.plus-hour}")
    private int refreshTokenPlusHour;

    private final TokenHelperIfs tokenHelperIfs;
    private final HttpSession httpSession;

    public TokenDto issueAccessToken(String userId) {
        Map<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(String userId) {
        Map<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        String userId = validationToken(refreshToken);

        String storedToken = (String) httpSession.getAttribute("refreshToken:" + userId);
        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
        }

        return issueRefreshToken(userId);
    }

    public String validationToken(String token) {
        Map<String, Object> userData = tokenHelperIfs.validationTokenWithThrow(token);

        Object userId = userData.get(USER_ID);
        Objects.requireNonNull(userId, () -> {
            throw new TokenException(ErrorCode.NULL_POINT);
        });
        return userId.toString();
    }

    public void saveRefreshToken(String userId, String refreshToken) {
        int expirationInSeconds = refreshTokenPlusHour * 60 * 60; // 시간을 초 단위로 변환

        httpSession.setAttribute(REFRESH_TOKEN + userId, refreshToken);
        httpSession.setMaxInactiveInterval(expirationInSeconds); // 세션 만료 시간 설정
    }

    public void deleteRefreshToken(String userId) {
        httpSession.removeAttribute(REFRESH_TOKEN + userId);
    }

}
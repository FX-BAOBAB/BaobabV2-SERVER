package user.security.jwt.service;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.security.jwt.ifs.TokenHelperIfs;
import user.security.jwt.model.TokenDto;

@Service
@RequiredArgsConstructor
public class TokenHelperService {

    @Value("${jwt.refresh-token.plus-hour}")
    private int refreshTokenPlusHour;

    private final TokenHelperIfs tokenHelperIfs;
    private final HttpSession httpSession;

    public TokenDto issueAccessToken(String userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(String userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        String userId = validationToken(refreshToken);

        String storedToken = (String) httpSession.getAttribute("refreshToken:" + userId);
        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new RuntimeException("Invalid refresh token.");
            // throw new TokenException(TokenErrorCode.INVALID_TOKEN); // TODO: 예외처리
        }

        return issueRefreshToken(userId);
    }

    public String validationToken(String token) {
        Map<String, Object> userData = tokenHelperIfs.validationTokenWithThrow(token);

        Object userId = userData.get("userId");
        Objects.requireNonNull(userId, () -> {
            throw new RuntimeException("토큰 에러");
//            throw new TokenException(ErrorCode.NULL_POINT); // TODO 예외처리
        });
        return userId.toString();
    }

    public void saveRefreshToken(String userId, String refreshToken) {
        int expirationInSeconds = refreshTokenPlusHour * 60 * 60; // 시간을 초 단위로 변환

        httpSession.setAttribute("refreshToken:" + userId, refreshToken);
        httpSession.setMaxInactiveInterval(expirationInSeconds); // 세션 만료 시간 설정
    }

    public void deleteRefreshToken(String userId) {
        httpSession.removeAttribute("refreshToken:" + userId);
    }

}

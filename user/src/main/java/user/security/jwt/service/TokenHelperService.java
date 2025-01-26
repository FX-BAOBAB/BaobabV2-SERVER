package user.security.jwt.service;

import global.errorcode.ErrorCode;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserRole;
import user.core.common.error.TokenErrorCode;
import user.core.common.exception.token.TokenException;
import user.security.jwt.ifs.TokenHelperIfs;
import user.security.jwt.model.JwtInfoDto;
import user.security.jwt.model.TokenDto;

@Service
@RequiredArgsConstructor
public class TokenHelperService {

    private final String USER_ID = "userId";
    private final String NICK_NAME = "nickName";
    private final String ROLE = "role";
    private final String REFRESH_TOKEN = "refreshToken:";

    @Value("${jwt.refresh-token.plus-hour}")
    private int refreshTokenPlusHour;

    private final TokenHelperIfs tokenHelperIfs;
    private final HttpSession httpSession;

    public TokenDto issueAccessToken(JwtInfoDto jwtInfoDto) {
        Map<String, Object> data = new HashMap<>();
        data.put(USER_ID, jwtInfoDto.getUserId());
        data.put(NICK_NAME, jwtInfoDto.getNickName());
        data.put(ROLE, jwtInfoDto.getRole());
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(JwtInfoDto jwtInfoDto) {
        Map<String, Object> data = new HashMap<>();
        data.put(USER_ID, jwtInfoDto.getUserId());
        data.put(NICK_NAME, jwtInfoDto.getNickName());
        data.put(ROLE, jwtInfoDto.getRole());
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        JwtInfoDto jwtInfoDto = validationToken(refreshToken);

        String storedToken = (String) httpSession.getAttribute(REFRESH_TOKEN + jwtInfoDto.getUserId());
        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
        }

        return issueRefreshToken(jwtInfoDto);
    }

    public JwtInfoDto validationToken(String token) {
        Map<String, Object> userData = tokenHelperIfs.validationTokenWithThrow(token);

        Object userId = userData.get(USER_ID);
        Object nickName = userData.get(NICK_NAME);
        Object role = userData.get(ROLE);
        Objects.requireNonNull(userId, () -> {
            throw new TokenException(ErrorCode.NULL_POINT);
        });
        return JwtInfoDto.builder()
            .userId(userId.toString())
            .nickName(nickName.toString())
            .role(UserRole.valueOf(role.toString()))
            .build();
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

package user.security.jwt.service;

import global.enums.DeviceType;
import global.errorcode.ErrorCode;
import global.enums.UserRole;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.repository.UserDocument;
import user.core.common.error.TokenErrorCode;
import user.core.common.exception.token.TokenException;
import user.security.jwt.ifs.TokenHelperIfs;
import user.security.jwt.model.TokenDto;
import user.security.jwt.model.TokenInfoDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenHelperService {

    private final String REFRESH_TOKEN = "refreshToken";
    private final String USER_ID = "userId";
    private final String USER_ROLE = "userRole";

    @Value("${jwt.refresh-token.web.plus-hour}")
    private int webRefreshTokenPlusHour;

    @Value("${jwt.refresh-token.app.plus-hour}")
    private int appRefreshTokenPlusHour;

    private final TokenHelperIfs tokenHelperIfs;
    private final HttpSession httpSession;

    public TokenDto issueAccessToken(String userId, UserRole userRole) {
        Map<String, Object> data = createTokenData(userId, userRole);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(UserDocument userDocument, DeviceType deviceType) {
        Map<String, Object> data = createTokenData(userDocument.getId(), userDocument.getRole());
        return tokenHelperIfs.issueRefreshToken(data, deviceType);
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

    public void saveRefreshToken(String refreshToken, DeviceType deviceType) {
        int expirationInSeconds = calculateExpirationInSeconds(deviceType);

        httpSession.setAttribute(REFRESH_TOKEN, refreshToken);
        httpSession.setMaxInactiveInterval(expirationInSeconds);
    }

    private Map<String, Object> createTokenData(String userId, UserRole userRole) {
        Map<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        data.put(USER_ROLE, userRole);
        return data;
    }

    private int calculateExpirationInSeconds(DeviceType deviceType) {
        if (deviceType == null || deviceType == DeviceType.WEB) {
            return webRefreshTokenPlusHour * 60 * 60;
        } else {
            return appRefreshTokenPlusHour * 60 * 60;
        }
    }

}
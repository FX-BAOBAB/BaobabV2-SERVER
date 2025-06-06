package user.security.jwt.helper;

import global.enums.DeviceType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import user.core.common.error.TokenErrorCode;
import user.core.common.exception.token.TokenException;
import user.core.common.exception.token.TokenExpiredException;
import user.core.common.exception.token.TokenSignatureException;
import user.security.jwt.ifs.TokenHelperIfs;
import user.security.jwt.model.TokenDto;

@Slf4j
@Component
public class TokenHelper implements TokenHelperIfs {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${jwt.refresh-token.web.plus-hour}")
    private Long webRefreshTokenPlusHour;

    @Value("${jwt.refresh-token.app.plus-hour}")
    private Long appRefreshTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        return getTokenDto(data, accessTokenPlusHour);
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data, DeviceType deviceType) {
        if (deviceType == null || deviceType == DeviceType.WEB) {
            return getTokenDto(data, webRefreshTokenPlusHour);
        }
        return getTokenDto(data, appRefreshTokenPlusHour);
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        JwtParser parser = Jwts.parser().verifyWith(key).build();

        try{
            Jws<Claims> result = parser.parseSignedClaims(token);
            return new HashMap<>(result.getPayload());
        }catch (Exception e){
            if (e instanceof SignatureException){
                // 토큰 유효하지 않음
                throw new TokenSignatureException(TokenErrorCode.INVALID_TOKEN,e);
            }else if (e instanceof ExpiredJwtException){
                // 토큰 만료
                throw new TokenExpiredException(TokenErrorCode.EXPIRED_TOKEN,e);
            }else {
                // 그 외
                throw new TokenException(TokenErrorCode.TOKEN_EXCEPTION,e);
            }
        }
    }

    private TokenDto getTokenDto(Map<String, Object> data, Long refreshTokenPlusHour) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        Date expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwtToken = Jwts.builder()
            .signWith(key)
            .claims(data)
            .expiration(expiredAt)
            .compact();

        return TokenDto.builder()
            .token(jwtToken)
            .expiredAt(expiredLocalDateTime)
            .build();
    }

}

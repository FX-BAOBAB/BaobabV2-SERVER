package gateway.filter;

import gateway.common.error.TokenErrorCode;
import gateway.common.exception.token.NotPermittedException;
import gateway.common.exception.token.TokenException;
import gateway.common.exception.token.TokenExpiredException;
import gateway.common.exception.token.TokenSignatureException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final String PUBLIC_API_PREFIX = "/open-api";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int AUTH_HEADER_BEGIN_INDEX = BEARER_PREFIX.length();

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (path.contains(PUBLIC_API_PREFIX)) {
            return chain.filter(exchange);
        }

        return filterPrivateApi(exchange, chain);
    }

    private Mono<Void> filterPrivateApi(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = getAuthHeader(exchange);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new NotPermittedException(TokenErrorCode.NOT_PERMITTED);
        }

        String accessToken = authHeader.substring(AUTH_HEADER_BEGIN_INDEX);
        return validateToken(exchange, chain, accessToken);
    }

    private String getAuthHeader(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        return headers.getFirst(HttpHeaders.AUTHORIZATION);
    }

    private Mono<Void> validateToken(
        ServerWebExchange exchange,
        GatewayFilterChain chain,
        String accessToken) {

        String userId = validationTokenWithThrow(accessToken).get("userId").toString();

        ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("x-user-id", userId)
                    .build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    private Map<String, Object> validationTokenWithThrow(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        JwtParser parser = Jwts.parser().verifyWith(key).build();

        try{
            Jws<Claims> result = parser.parseSignedClaims(token);
            return new HashMap<>(result.getPayload());
        }catch (Exception e){
            if (e instanceof SignatureException){
                throw new TokenSignatureException(TokenErrorCode.INVALID_TOKEN, e);
            }else if (e instanceof ExpiredJwtException){
                throw new TokenExpiredException(TokenErrorCode.EXPIRED_TOKEN, e);
            }else {
                throw new TokenException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
package gateway.security.filter;

import gateway.common.error.TokenErrorCode;
import gateway.common.exception.token.NotPermittedException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final String PUBLIC_API_PREFIX = "/open-api";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int AUTH_HEADER_BEGIN_INDEX = BEARER_PREFIX.length();

    @Value("${token.validation.url}")
    private String tokenValidationUrl;

    private final WebClient webClient;

    public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

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

    private Mono<Void> validateToken(ServerWebExchange exchange, GatewayFilterChain chain,
        String accessToken) {
        return webClient.post()
            .uri(tokenValidationUrl)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class).flatMap(errorBody -> {
                    exchange.getResponse().setStatusCode(response.statusCode());
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
                    DataBuffer buffer = bufferFactory.wrap(errorBody.getBytes(StandardCharsets.UTF_8));
                    return exchange.getResponse().writeWith(Mono.just(buffer)).then(Mono.empty());
                })
            )
            .bodyToMono(Map.class)
            .flatMap(response -> {
                Map<String, Object> body = (Map<String, Object>) response.get("body");
                String userId = body.get("userId").toString();

                ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("x-user-id", userId)
                    .build();

                return chain.filter(exchange.mutate().request(request).build());
            });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
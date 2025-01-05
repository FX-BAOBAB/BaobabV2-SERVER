package user.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.token.TokenException;
import user.security.jwt.service.TokenHelperService;
import user.security.service.AuthorizationService;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthorizationService authorizationService;
    private final TokenHelperService tokenHelperService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        // JWT 가 헤더에 존재하는 경우
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            //JWT 유효성 검증
            String userId = tokenHelperService.validationToken(token);

            if (userId == null) {
                throw new TokenException(UserErrorCode.USER_NOT_FOUND);
            }

            //유저와 토큰 일치 시 userDetails 생성
            UserDetails userDetails = authorizationService.loadUserByUsername(userId);

            if (userDetails != null) {
                //UserDetsils, Password, Role -> 접근권한 인증 Token 생성
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //현재 Request의 Security Context에 접근권한 설정
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(request, response); // 다음 필터로 넘기기

    }
}

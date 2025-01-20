package article.core.common.interceptor;

import article.core.common.error.user.UserErrorCode;
import article.core.common.exception.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String X_USER_ID = "x-user-id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        log.info("userId: {}", request.getHeader(X_USER_ID));

        // Gateway 에서 전달한 Header 추출
        String userId = request.getHeader(X_USER_ID);

        if (userId == null) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND,
                "x-user-id header 가 존재하지 않습니다.");
        }

        RequestAttributes requestContext = Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes());

        requestContext.setAttribute(X_USER_ID, userId, RequestAttributes.SCOPE_REQUEST);

        return true;

    }
}

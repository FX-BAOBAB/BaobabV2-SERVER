package global.interceptor;

import global.errorcode.ErrorCode;
import global.exception.UnauthorizedException;
import global.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    public static final String X_USER_ID = "x-user-id";
    public static final String X_USER_ROLE = "x-user-role";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        String userId = request.getHeader(X_USER_ID);
        String userRole = request.getHeader(X_USER_ROLE);

        if (userId == null || !UserRole.MASTER.name().equals(userRole)) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        RequestAttributes requestContext = Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes());
        requestContext.setAttribute(X_USER_ID, userId, RequestAttributes.SCOPE_REQUEST);
        requestContext.setAttribute(X_USER_ROLE, userRole, RequestAttributes.SCOPE_REQUEST);

        return true;
    }
}

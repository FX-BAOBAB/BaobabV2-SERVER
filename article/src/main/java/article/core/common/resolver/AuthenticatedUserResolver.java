package article.core.common.resolver;

import article.core.common.error.user.UserErrorCode;
import article.core.common.exception.user.UserNotFoundException;
import global.annotation.AuthenticatedUser;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean annotation = parameter.hasParameterAnnotation(AuthenticatedUser.class);

        boolean parameterType = parameter.getParameterType().equals(AuthUser.class);

        return (annotation && parameterType);

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // supportsParameter true 시 실행

        // request context holder 에서 user id 찾기
        RequestAttributes requestContext = Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes());

        Object userId = requestContext.getAttribute("x-user-id",
            RequestAttributes.SCOPE_REQUEST);

        if (userId == null) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }

        return AuthUser.builder()
            .userId(userId.toString())
            .build();
    }

}

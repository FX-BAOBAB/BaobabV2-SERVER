package user.core.common.aop;

import global.api.Api;
import global.resolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import user.adapter.input.web.request.UserUnRegisterRequest;
import user.adapter.input.web.request.UserUpdateRequest;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.output.UserPersistencePort;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.token.UserNotFoundException;
import user.core.common.exception.user.PasswordMismatchException;

@Aspect
@Component
@RequiredArgsConstructor
public class PasswordCheckAdvice {

    private final UserPersistencePort userPersistencePort;

    @Pointcut("@annotation(user.core.common.annotation.PasswordCheck)")
    public void passwordCheckPointcut() {

    }

    @Before("passwordCheckPointcut()")
    public void checkPassword(JoinPoint joinPoint) {

        for (Object arg : joinPoint.getArgs()) {

            if (arg instanceof Api) {
                Api<?> apiRequest = (Api<?>) arg;
                Object requestBody = apiRequest.getBody();

                validateUserUnRegisterRequest(requestBody, joinPoint);
                validateUserUpdateRequest(requestBody, joinPoint);
            }
        }

    }

    private void validateUserUpdateRequest(Object requestBody, JoinPoint joinPoint) {
        if (requestBody instanceof UserUpdateRequest) {
            UserUpdateRequest userUnRegisterRequest = (UserUpdateRequest) requestBody;

            String currentPassword = getCurrentPassword(joinPoint);

            // Password 검증
            checkPasswordWithThrow(userUnRegisterRequest.getPassword(), currentPassword);
        }
    }

    private void validateUserUnRegisterRequest(Object requestBody, JoinPoint joinPoint) {
        if (requestBody instanceof UserUnRegisterRequest) {
            UserUnRegisterRequest userUnRegisterRequest = (UserUnRegisterRequest) requestBody;

            String currentPassword = getCurrentPassword(joinPoint);

            // Password 검증
            checkPasswordWithThrow(userUnRegisterRequest.getPassword(), currentPassword);
        }
    }

    private String getCurrentPassword(JoinPoint joinPoint) {
        String userId = getUserIdBy(joinPoint);
        return userPersistencePort.getUserDocument(userId, UserStatus.REGISTERED).getUserAccount()
            .getPassword();
    }

    private static void checkPasswordWithThrow(String inputPassword, String currentPassword) {
        if (!BCrypt.checkpw(inputPassword, currentPassword)) {
            throw new PasswordMismatchException(UserErrorCode.PASSWORD_MISMATCH);
        }
    }

    private String getUserIdBy(JoinPoint joinPoint) {
        // joinPoint 에서 AuthUser 추출
        for (Object methodArg : joinPoint.getArgs()) {
            if (methodArg instanceof AuthUser) {
                return ((AuthUser) methodArg).getUserId();
            }
        }
        throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
    }

}

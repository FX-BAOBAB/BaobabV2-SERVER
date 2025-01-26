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
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
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



        String userId = null;
        UserUnRegisterRequest userUnRegisterRequest = null;

        for (Object arg : joinPoint.getArgs()) {

            // userId 추출
            if (arg instanceof AuthUser) {
                AuthUser authUser = (AuthUser) arg;
                userId = authUser.getUserId();
            }

            // UserUnRegisterRequest 추출
            if (arg instanceof Api) {
                Api<?> apiRequest = (Api<?>) arg;
                Object requestBody = apiRequest.getBody();

                if (requestBody instanceof UserUnRegisterRequest) {
                    userUnRegisterRequest = (UserUnRegisterRequest) requestBody;
                }
            }
        }

        if (userId == null) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }


        UserDocument userDocument = userPersistencePort.getUserDocument(
            userId, UserStatus.REGISTERED);

        // Password 검증
        if (!BCrypt.checkpw(userUnRegisterRequest.getPassword(),
            userDocument.getAccount().getPassword())) {
            throw new PasswordMismatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

    }
}

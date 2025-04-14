package user.core.common.aop;

import global.api.Api;
import global.resolver.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import user.adapter.input.web.request.DuplicationEmailRequest;
import user.adapter.input.web.request.DuplicationNickNameRequest;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.input.web.request.UserUpdateRequest;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.application.port.output.UserPersistencePort;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.user.UserNotFoundException;
import user.core.common.exception.user.EmailExistsException;
import user.core.common.exception.user.NickNameExistsException;
import user.core.common.exception.user.UserExistsException;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DupleCheckAdvice {

    private final UserPersistencePort userPersistencePort;

    @Pointcut("@annotation(user.core.common.annotation.DupleCheck)")
    public void dupleCheckPointcut() {

    }

    @Before("dupleCheckPointcut()")
    public void checkDuplicateUser(JoinPoint joinPoint) {

        var args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof UserUpdateRequest) {
                checkUpdateRequest(arg, joinPoint);
            }

            if (arg instanceof Api) {
                Api<?> apiRequest = (Api<?>) arg;
                Object requestBody = apiRequest.getBody();

                // Api<?> 인 경우
                checkEmailRequest(requestBody);
                checkNickNameRequest(requestBody);
                checkUserRegisterRequest(requestBody);
            }
            // Api<?> 가 아닌 경우
        }

    }

    private void checkUserRegisterRequest(Object requestBody) {
        if (requestBody instanceof UserRegisterRequest) {
            UserRegisterRequest userRegisterRequest = (UserRegisterRequest) requestBody;

            // email & nickName 검증
            boolean isRegisteredEmail = checkEmailDuplicate(userRegisterRequest.getEmail());
            boolean isRegisteredNickName = checkNickNameDuplicate(
                userRegisterRequest.getNickName());

            if (isRegisteredEmail && isRegisteredNickName) {
                throw new UserExistsException(UserErrorCode.USER_EXISTS);
            } else if (isRegisteredEmail) {
                throw new EmailExistsException(UserErrorCode.EMAIL_EXISTS);
            } else if (isRegisteredNickName) {
                throw new NickNameExistsException(UserErrorCode.NICKNAME_EXISTS);
            }
        }
    }

    private void checkUpdateRequest(Object requestBody, JoinPoint joinPoint) {
        if (requestBody instanceof UserUpdateRequest) {
            UserUpdateRequest userUpdateRequest = (UserUpdateRequest) requestBody;

            // userId 추출
            String userId = getUserIdBy(joinPoint);
            String currentNickName = getUserDocument(userId, UserStatus.REGISTERED).getNickName(); // 현재 닉네임
            String requestedNickName = userUpdateRequest.getNickName(); // 요청된 닉네임

            // 닉네임이 변경되었을 경우만 중복 체크
            if (!currentNickName.equals(requestedNickName)) {
                boolean isRegisteredNickName = checkNickNameDuplicate(requestedNickName);

                if (isRegisteredNickName) {
                    throw new NickNameExistsException(UserErrorCode.NICKNAME_EXISTS);
                }
            }
        }
    }

    private void checkNickNameRequest(Object requestBody) {
        if (requestBody instanceof DuplicationNickNameRequest) {
            DuplicationNickNameRequest duplicationNickNameRequest = (DuplicationNickNameRequest) requestBody;

            boolean isRegisteredNickName = checkNickNameDuplicate(
                duplicationNickNameRequest.getNickName());

            if (isRegisteredNickName) {
                throw new NickNameExistsException(UserErrorCode.NICKNAME_EXISTS);
            }
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

    private void checkEmailRequest(Object requestBody) {
        if (requestBody instanceof DuplicationEmailRequest) {
            DuplicationEmailRequest duplicationEmailRequest = (DuplicationEmailRequest) requestBody;

            boolean isRegisteredEmail = checkEmailDuplicate(
                duplicationEmailRequest.getEmail());

            if (isRegisteredEmail) {
                throw new EmailExistsException(UserErrorCode.EMAIL_EXISTS);
            }
        }
    }

    private boolean checkEmailDuplicate(String email) {
        return userPersistencePort.checkEmailDuplicate(email);
    }

    private boolean checkNickNameDuplicate(String nickName) {
        return userPersistencePort.checkNickNameDuplicate(nickName);
    }

    private UserDocument getUserDocument(String userId, UserStatus status) {
        return userPersistencePort.getUserDocument(userId,
            status);
    }

}
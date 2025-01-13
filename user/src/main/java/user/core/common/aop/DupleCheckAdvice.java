package user.core.common.aop;

import global.api.Api;
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
            if (arg instanceof Api) {
                Api<?> apiRequest = (Api<?>) arg;
                Object requestBody = apiRequest.getBody();

                // Api<?> 인 경우
                checkEmailRequest(requestBody);
                checkNickNameRequest(requestBody);
            }

            // Api<?> 가 아닌 경우
            checkUpdateRequest(joinPoint, arg);
            checkUserRegisterRequest(arg);

        }

    }

    private void checkUserRegisterRequest(Object arg) {
        if (arg instanceof UserRegisterRequest) {
            UserRegisterRequest userRegisterRequest = (UserRegisterRequest) arg;

            // email & nickName 검증
            boolean isRegisteredEmail = checkEmailDuplicate(userRegisterRequest.getEmail());
            boolean isRegisteredNickName = checkNickNameDuplicate(
                userRegisterRequest.getNickName());

            if (isRegisteredEmail || isRegisteredNickName) {
                throw new UserExistsException(UserErrorCode.USER_EXISTS);
            }
        }
    }

    private void checkUpdateRequest(JoinPoint joinPoint, Object arg) {
        if (arg instanceof UserUpdateRequest) {
            UserUpdateRequest userUpdateRequest = (UserUpdateRequest) arg;

            // userId 추출
            String userId = null;
            Object[] methodArgs = joinPoint.getArgs();
            for (Object methodArg : methodArgs) {
                if (methodArg instanceof String) {
                    userId = (String) methodArg;
                    break;
                }
            }

            String currentNickName = getUserDocument(userId,
                UserStatus.REGISTERED).getNickName(); // 현재 닉네임
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
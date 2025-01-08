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
            if(arg instanceof Api) {
                Api<?> apiRequest = (Api<?>) arg;
                Object requestBody = apiRequest.getBody();

                if (requestBody instanceof UserRegisterRequest) {
                    UserRegisterRequest userRegisterRequest = (UserRegisterRequest) requestBody;

                    // email & nickName 검증
                    boolean isRegisteredEmail = userPersistencePort.checkEmailDuplicate(userRegisterRequest.getEmail());
                    boolean isRegisteredNickName = userPersistencePort.checkNickNameDuplicate(userRegisterRequest.getNickName());

                    if (isRegisteredEmail || isRegisteredNickName) {
                        throw new UserExistsException(UserErrorCode.USER_EXISTS);
                    }
                }

                if(requestBody instanceof UserUpdateRequest) { // TODO 닉네임 중복 예외처리, 요청자 닉네임 중복 고려
                    UserUpdateRequest userUpdateRequest = (UserUpdateRequest) requestBody;
                }

                if(requestBody instanceof DuplicationEmailRequest) {
                    DuplicationEmailRequest duplicationEmailRequest = (DuplicationEmailRequest) requestBody;

                    boolean isRegisteredEmail = userPersistencePort.checkEmailDuplicate(duplicationEmailRequest.getEmail());

                    if (isRegisteredEmail) {
                        throw new EmailExistsException(UserErrorCode.EMAIL_EXISTS);
                    }
                }

                if(requestBody instanceof DuplicationNickNameRequest) {
                    DuplicationNickNameRequest duplicationNickNameRequest = (DuplicationNickNameRequest) requestBody;

                    boolean isRegisteredNickName = userPersistencePort.checkNickNameDuplicate(duplicationNickNameRequest.getNickName());

                    if (isRegisteredNickName) {
                        throw new NickNameExistsException(UserErrorCode.NICKNAME_EXISTS);
                    }
                }

            }
        }
    }

}

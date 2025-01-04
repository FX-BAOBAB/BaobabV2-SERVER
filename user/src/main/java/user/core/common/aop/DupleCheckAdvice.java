package user.core.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import user.adapter.input.web.request.UserRegisterRequest;
import user.application.port.output.UserPersistencePort;

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
            if(arg instanceof UserRegisterRequest) {
                UserRegisterRequest userRegisterRequest = (UserRegisterRequest) arg;

                // email & nickName 검증
                boolean isRegisteredEmail = userPersistencePort.checkEmailDuplicate(userRegisterRequest.getEmail());
                boolean isRegisteredNickName = userPersistencePort.checkNickNameDuplicate(userRegisterRequest.getNickName());

                if(isRegisteredEmail || isRegisteredNickName) {
                    throw new RuntimeException("이미 존재하는 계정입니다."); // TODO 예외처리
                }
            }
        }
    }

}

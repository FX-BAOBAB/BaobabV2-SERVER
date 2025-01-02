package user.core.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import user.adapter.input.web.request.UserRegisterRequest;
import user.application.port.output.AccountReaderPort;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DupleCheckAdvice {

    private final AccountReaderPort accountReaderPort;

    @Pointcut("@annotation(user.core.common.annotation.DupleCheck)")
    public void dupleCheckPointcut() {
    }

    @Before("dupleCheckPointcut()")
    public void checkDuplicateUser(JoinPoint joinpoint) {

        var args = joinpoint.getArgs();

        for (Object arg : args) {
            if(arg instanceof UserRegisterRequest) {
                UserRegisterRequest userRegisterRequest = (UserRegisterRequest) arg;

                // TODO nickName 검증 Logic 제외??
                // nickName & email 검증
                boolean isRegisteredEmail = accountReaderPort.checkEmailDuplicate(userRegisterRequest.getEmail());
                boolean isRegisteredName = accountReaderPort.checkNickNameDuplicate(userRegisterRequest.getNickName());

                if(isRegisteredEmail || isRegisteredName) {
                    throw new RuntimeException("이미 존재하는 계정입니다."); // TODO 예외처리
                }
            }
        }
    }

}
package chat.core.common.aop;

import chat.application.port.output.ChatRoomPersistencePort;
import chat.core.common.error.ChatRoomErrorCode;
import chat.core.common.exception.chatroom.ChatRoomExistsException;
import global.resolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ChatRoomAspect {

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    @Pointcut("@annotation(chat.core.common.annotation.CheckChatRoomExists)")
    public void checkChatRoomExistencePointcut() {

    }

    @Before("checkChatRoomExistencePointcut()")
    public void checkChatRoomExistence(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        String userId = null;
        String articleId = null;

        for (Object arg : args) {
            if (arg instanceof AuthUser) {
                userId = ((AuthUser) arg).getUserId();
            }

            if (arg instanceof String) {
                articleId = arg.toString();
            }
        }

        if (articleId == null || userId == null) {
            throw new IllegalArgumentException();
        }

        boolean existsChatRoom = chatRoomPersistencePort.existsChatRoom(articleId, userId);

        if (existsChatRoom) {
            throw new ChatRoomExistsException(ChatRoomErrorCode.CHAT_ROOM_EXISTS);
        }

    }


}

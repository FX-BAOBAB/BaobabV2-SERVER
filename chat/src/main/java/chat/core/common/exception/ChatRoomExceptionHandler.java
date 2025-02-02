package chat.core.common.exception;

import chat.core.common.error.ChatRoomErrorCode;
import chat.core.common.exception.chatroom.ChatRoomExistsException;
import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ChatRoomExceptionHandler {

    @ExceptionHandler(value = ChatRoomExistsException.class)
    public ResponseEntity<Api<Object>> existsChatRoomException(ChatRoomExistsException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Api.ERROR(ChatRoomErrorCode.CHAT_ROOM_EXISTS));
    }

}

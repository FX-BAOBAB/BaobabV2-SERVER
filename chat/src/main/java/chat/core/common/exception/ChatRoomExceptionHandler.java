package chat.core.common.exception;

import chat.core.common.error.ChatErrorCode;
import chat.core.common.exception.chatroom.ChatRoomCreateFailedException;
import chat.core.common.exception.chatroom.ChatRoomNotFoundException;
import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ChatRoomExceptionHandler {

    @ExceptionHandler(value = ChatRoomNotFoundException.class)
    public ResponseEntity<Api<Object>> notFoundChatRoom(ChatRoomNotFoundException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(ChatErrorCode.CHAT_ROOM_NOT_FOUND));
    }

    @ExceptionHandler(value = ChatRoomCreateFailedException.class)
    public ResponseEntity<Api<Object>> createFailedChatRoomException(ChatRoomCreateFailedException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Api.ERROR(ChatErrorCode.CHAT_ROOM_CREATION_FAILED));
    }

}

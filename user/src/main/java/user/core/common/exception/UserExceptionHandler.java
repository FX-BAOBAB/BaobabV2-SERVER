package user.core.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.token.UserNotFoundException;
import user.core.common.exception.user.UserExistsException;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<Api<Object>> existsUserException(UserExistsException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Api.ERROR(UserErrorCode.EXIST_USER));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Api<Object>> notFoundUserException(UserNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(UserErrorCode.USER_NOT_FOUND));
    }


}

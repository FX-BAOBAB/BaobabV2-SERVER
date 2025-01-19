package user.core.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.token.UserNotFoundException;
import user.core.common.exception.user.EmailExistsException;
import user.core.common.exception.user.NickNameExistsException;
import user.core.common.exception.user.PasswordMismatchException;
import user.core.common.exception.user.UserExistsException;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<Api<Object>> existsUserException(UserExistsException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Api.ERROR(UserErrorCode.USER_EXISTS));
    }

    @ExceptionHandler(value = EmailExistsException.class)
    public ResponseEntity<Api<Object>> existsEmailException(EmailExistsException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Api.ERROR(UserErrorCode.EMAIL_EXISTS));
    }

    @ExceptionHandler(value = NickNameExistsException.class)
    public ResponseEntity<Api<Object>> existsNickNameException(NickNameExistsException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Api.ERROR(UserErrorCode.NICKNAME_EXISTS));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Api<Object>> notFoundUserException(UserNotFoundException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(UserErrorCode.USER_NOT_FOUND));
    }

    @ExceptionHandler(value = PasswordMismatchException.class)
    public ResponseEntity<Api<Object>> mismatchPasswordException(PasswordMismatchException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Api.ERROR(UserErrorCode.PASSWORD_MISMATCH));
    }

}

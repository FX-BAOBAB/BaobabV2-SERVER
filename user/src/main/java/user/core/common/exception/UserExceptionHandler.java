package user.core.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.user.EmailVerificationExpiredException;
import user.core.common.exception.user.UserNotFoundException;
import user.core.common.exception.user.EmailExistsException;
import user.core.common.exception.user.NickNameExistsException;
import user.core.common.exception.user.PasswordMismatchException;
import user.core.common.exception.user.UserExistsException;
import user.core.common.exception.user.EmailVerificationCodeMismatchException;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<Api<Object>> existsUserException(UserExistsException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Api.ERROR(UserErrorCode.USER_EXISTS));
    }

    @ExceptionHandler(value = EmailExistsException.class)
    public ResponseEntity<Api<Object>> existsEmailException(EmailExistsException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Api.ERROR(UserErrorCode.EMAIL_EXISTS));
    }

    @ExceptionHandler(value = NickNameExistsException.class)
    public ResponseEntity<Api<Object>> existsNickNameException(NickNameExistsException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Api.ERROR(UserErrorCode.NICKNAME_EXISTS));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Api<Object>> notFoundUserException(UserNotFoundException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(UserErrorCode.USER_NOT_FOUND));
    }

    @ExceptionHandler(value = PasswordMismatchException.class)
    public ResponseEntity<Api<Object>> mismatchPasswordException(PasswordMismatchException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Api.ERROR(UserErrorCode.PASSWORD_MISMATCH));
    }

    @ExceptionHandler(value = EmailVerificationExpiredException.class)
    public ResponseEntity<Api<Object>> ExpiredEmailVerificationException(EmailVerificationExpiredException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.GONE)
            .body(Api.ERROR(UserErrorCode.EMAIL_VERIFICATION_EXPIRED));
    }

    @ExceptionHandler(value = EmailVerificationCodeMismatchException.class)
    public ResponseEntity<Api<Object>> mismatchEmailVerificationCode(
        EmailVerificationCodeMismatchException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Api.ERROR(UserErrorCode.EMAIL_VERIFICATION_CODE_MISMATCH));
    }

}

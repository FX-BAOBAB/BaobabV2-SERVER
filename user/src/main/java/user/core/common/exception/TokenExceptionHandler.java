package user.core.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import user.core.common.error.TokenErrorCode;
import user.core.common.exception.token.TokenException;
import user.core.common.exception.token.TokenExpiredException;
import user.core.common.exception.token.TokenSignatureException;

@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(value = TokenException.class)
    public ResponseEntity<Api<Object>> tokenException(TokenException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Api.ERROR(TokenErrorCode.TOKEN_EXCEPTION));
    }

    @ExceptionHandler(value = TokenSignatureException.class)
    public ResponseEntity<Api<Object>> tokenSignatureException(TokenSignatureException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Api.ERROR(TokenErrorCode.INVALID_TOKEN));
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<Api<Object>> tokenExpiredException(TokenExpiredException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Api.ERROR(TokenErrorCode.EXPIRED_TOKEN));
    }

}

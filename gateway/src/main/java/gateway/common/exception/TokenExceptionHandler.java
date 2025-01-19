package gateway.common.exception;

import gateway.common.error.TokenErrorCode;
import gateway.common.exception.token.NotPermittedException;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(value = NotPermittedException.class)
    public ResponseEntity<Api<Object>> notPermittedException(NotPermittedException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Api.ERROR(TokenErrorCode.NOT_PERMITTED));
    }

}

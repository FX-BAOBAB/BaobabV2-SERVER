package token.core.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import token.core.common.error.FcmTokenErrorCode;
import token.core.common.exception.firebase.FirebaseInitializationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import token.core.common.exception.topic.FailedToSubscribeTopicException;

@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(value = FirebaseInitializationFailedException.class)
    public ResponseEntity<Api<Object>> firebaseInitializationFailedException(
        FirebaseInitializationFailedException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Api.ERROR(FcmTokenErrorCode.FIREBASE_INITIALIZATION_FAILED));
    }

    @ExceptionHandler(value = FailedToSubscribeTopicException.class)
    public ResponseEntity<Api<Object>> failedToSubscribeToTopicException(
        FailedToSubscribeTopicException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Api.ERROR(FcmTokenErrorCode.FAILED_TO_SUBSCRIBE_TOPIC));
    }

}
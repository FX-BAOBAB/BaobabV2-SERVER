package message.core.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import message.core.common.error.MessageErrorCode;
import message.core.common.exception.firebase.FirebaseInitializationFailedException;
import message.core.common.exception.message.FailedToSendMessageException;
import message.core.common.exception.topic.FailedToSubscribeTopicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MessageExceptionHandler {

    @ExceptionHandler(value = FirebaseInitializationFailedException.class)
    public ResponseEntity<Api<Object>> firebaseInitializationFailedException(
        FirebaseInitializationFailedException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Api.ERROR(MessageErrorCode.FIREBASE_INITIALIZATION_FAILED));
    }

    @ExceptionHandler(value = FailedToSendMessageException.class)
    public ResponseEntity<Api<Object>> failedToSendMessageException(FailedToSendMessageException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Api.ERROR(MessageErrorCode.FAILED_TO_SEND_MESSAGE));
    }

    @ExceptionHandler(value = FailedToSubscribeTopicException.class)
    public ResponseEntity<Api<Object>> failedToSubscribeToTopicException(
        FailedToSubscribeTopicException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Api.ERROR(MessageErrorCode.FAILED_TO_SUBSCRIBE_TOPIC));
    }
}
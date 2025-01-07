package file.core.common.exception;

import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageNotFoundException;
import file.core.common.exception.image.ImageStorageException;
import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ImageExceptionHandler {

    @ExceptionHandler(value = ImageNotFoundException.class)
    public ResponseEntity<Api<Object>> tokenException(ImageNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ImageErrorCode.IMAGE_NOT_FOUND));
    }

    @ExceptionHandler(value = ImageStorageException.class)
    public ResponseEntity<Api<Object>> tokenException(ImageStorageException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Api.ERROR(ImageErrorCode.IMAGE_STORAGE_ERROR));
    }


}

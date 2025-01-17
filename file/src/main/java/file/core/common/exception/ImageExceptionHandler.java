package file.core.common.exception;

import file.core.common.exception.image.ImageNotFoundException;
import file.core.common.exception.image.ImageStorageException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Component
public class ImageExceptionHandler {

    @ExceptionHandler(value = ImageNotFoundException.class)
    public void imageException(ImageNotFoundException e) {
        log.error("", e);

    }

    @ExceptionHandler(value = ImageStorageException.class)
    public void imageException(ImageStorageException e) {
        log.error("", e);

    }
}

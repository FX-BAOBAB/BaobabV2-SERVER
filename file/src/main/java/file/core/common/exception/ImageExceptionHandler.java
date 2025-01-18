package file.core.common.exception;

import file.core.common.exception.image.ImageStorageException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Component
public class ImageExceptionHandler {

    @ExceptionHandler(value = ImageStorageException.class)
    public void imageException(ImageStorageException e) {
        log.error("", e);
    }
}

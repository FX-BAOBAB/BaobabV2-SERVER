package file.core.common.utils;

import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageMetaData;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

@Slf4j
public class RetryUtils {
    private static final int MAX_RETRIES = 5;
    private static final long INITIAL_DELAY = 1000;
    private static final long MAX_DELAY = 30000;

    public static <T> T performRetryableTask(Supplier<T> task, String taskName, String imageId) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return task.get();
            } catch (Exception e) {
                // 지연 시간 지수적으로 증가
                long delay = Math.min(
                        INITIAL_DELAY * (long) Math.pow(2, attempt - 1),
                        MAX_DELAY
                );
                try {
                    log.warn("Retrying {} operation for {} after {}ms delay", taskName, imageId, delay);
                    sleep(delay);
                } catch (InterruptedException ex) {
                    currentThread().interrupt();
                }
            }
        }

        log.error("Failed to {} after {} attempts: {}", taskName, MAX_RETRIES, imageId);
        throw new ImageStorageException(ImageErrorCode.IMAGE_DELETE_ERROR);
    }
}

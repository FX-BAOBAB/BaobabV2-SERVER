package file.core.aop;

import file.application.port.output.ErrorLogPersistencePort;
import file.core.common.exception.image.ImageException;
import file.domain.ErrorLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionLoggingAspect {

    private final ErrorLogPersistencePort errorLogPersistencePort;

    @Pointcut("execution(* file.core.LocalFileAndDBImageStorageService.deleteImage(..))")
    private void deleteInLocalFileAndDBImageStorageService() {}

    @Pointcut("execution(* file.core.LocalFileStorageService.deleteImage(..))")
    private void deleteInLocalFileStorageService() {}

    @Pointcut("execution(* file.core.MongoDBImageMetaDataService.deleteImage(..))")
    private void deleteInMongoDBImageMetaDataService() {}

    @Pointcut("execution(* file.adapter.output.LocalFileStorage.delete(..))")
    private void deleteInLocalFileStorage() {}

    @Pointcut("execution(* file.application.port.output.MongoDBImageMetaDataRepository.deleteById(..))")
    private void deleteInMongoDBImageMetaDataMetaDataRepository() {}

    @AfterThrowing(pointcut = "deleteInLocalFileAndDBImageStorageService() || " +
            "deleteInLocalFileStorageService() || " +
            "deleteInMongoDBImageMetaDataService() ||" +
            "deleteInLocalFileStorage() ||" +
            "deleteInMongoDBImageMetaDataMetaDataRepository()",
            throwing = "exception")
    public void logException(Throwable exception) {
        ErrorLog errorLog;
        if(exception instanceof ImageException imageException) {
            errorLog = ErrorLog.builder()
                    .code(imageException.getErrorCodeIfs().getErrorCode())
                    .timestamp(getFormattedCurrentTimestamp())
                    .isCustomException(true)
                    .description(imageException.getDescription())
                    .build();
        } else {
            errorLog = ErrorLog.builder()
                    .code(500)
                    .timestamp(getFormattedCurrentTimestamp())
                    .isCustomException(false)
                    .description("Image Delete Failed Cause: " + exception.getMessage())
                    .build();
        }

        log.error(errorLog.getDescription());

        errorLogPersistencePort.save(errorLog);

    }

    private String getFormattedCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(LocalDateTime.now());
    }
}

package file.application.port.output;

import file.domain.ErrorLog;

public interface ErrorLogPersistencePort {
    ErrorLog save(ErrorLog errorLog);
}

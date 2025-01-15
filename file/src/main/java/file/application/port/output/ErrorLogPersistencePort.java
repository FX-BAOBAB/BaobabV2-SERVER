package file.application.port.output;

import file.domain.ErrorLog;

public interface ErrorLogPersistencePort {
    void save(ErrorLog errorLog);
}

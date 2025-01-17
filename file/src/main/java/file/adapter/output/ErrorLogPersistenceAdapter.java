package file.adapter.output;

import file.application.port.output.ErrorLogPersistencePort;
import file.adapter.output.repository.MongoDBErrorLogRepository;
import file.domain.ErrorLog;
import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ErrorLogPersistenceAdapter implements ErrorLogPersistencePort {

    private final MongoDBErrorLogRepository mongoDBErrorLogRepository;

    @Override
    public void save(ErrorLog errorLog) {
        mongoDBErrorLogRepository.save(errorLog);
    }
}

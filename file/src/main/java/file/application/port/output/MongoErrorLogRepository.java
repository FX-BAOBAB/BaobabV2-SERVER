package file.application.port.output;


import file.domain.ErrorLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoErrorLogRepository extends MongoRepository<ErrorLog, String>, ErrorLogPersistencePort {
}

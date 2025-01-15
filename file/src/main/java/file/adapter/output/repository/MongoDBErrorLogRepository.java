package file.adapter.output.repository;


import file.domain.ErrorLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBErrorLogRepository extends MongoRepository<ErrorLog, String> {
}

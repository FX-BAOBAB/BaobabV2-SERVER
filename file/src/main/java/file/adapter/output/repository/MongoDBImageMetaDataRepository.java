package file.adapter.output.repository;

import file.domain.ImageMetaData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBImageMetaDataRepository extends MongoRepository<ImageMetaData, String> {
}

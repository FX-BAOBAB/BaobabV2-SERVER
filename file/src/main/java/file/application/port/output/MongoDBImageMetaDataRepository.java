package file.application.port.output;

import file.domain.ImageMetaData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoDBImageMetaDataRepository extends MongoRepository<ImageMetaData, String>, ImageMetaDataPersistencePort {
    ImageMetaData save(ImageMetaData metaData);
    boolean existsById(String imageId);
    Optional<ImageMetaData> findById(String imageId);
    void deleteById(String imageId);
}

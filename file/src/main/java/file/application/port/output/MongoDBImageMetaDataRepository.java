package file.application.port.output;

import file.domain.ImageMetaData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBImageMetaDataRepository extends MongoRepository<ImageMetaData, String>, ImageMetaDataPersistencePort {
    ImageMetaData save(ImageMetaData metaData);
    ImageMetaData deleteById(String imageId);
    boolean existsById(String imageId);
}

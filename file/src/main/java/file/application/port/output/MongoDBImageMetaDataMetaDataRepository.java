package file.application.port.output;

import file.domain.ImageMetaData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBImageMetaDataMetaDataRepository extends MongoRepository<ImageMetaData, ObjectId>, ImageMetaDataPersistencePort {
    ImageMetaData save(ImageMetaData metaData);
}

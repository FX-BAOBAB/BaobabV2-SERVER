package file.application.port.output;

import file.domain.ImageMetaData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBImageMetaDataRepository extends MongoRepository<ImageMetaData, ObjectId>, ImageMetaDataRepository {
    ImageMetaData save(ImageMetaData metaData);
}

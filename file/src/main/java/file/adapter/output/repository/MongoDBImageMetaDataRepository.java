package file.adapter.output.repository;

import file.domain.ImageKind;
import file.domain.ImageMetaData;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBImageMetaDataRepository extends MongoRepository<ImageMetaData, String> {

    Optional<ImageMetaData> findFirstByKind(ImageKind imageKind);

}

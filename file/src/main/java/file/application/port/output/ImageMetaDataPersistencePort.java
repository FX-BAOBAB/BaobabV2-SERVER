package file.application.port.output;

import file.domain.ImageKind;
import file.domain.ImageMetaData;

import java.util.Optional;

public interface ImageMetaDataPersistencePort {
    ImageMetaData save(ImageMetaData metaData);
    boolean existsById(String imageId);
    Optional<ImageMetaData> findById(String imageId);
    void deleteById(String imageId);
    Optional<ImageMetaData> findFirstByImageKind(ImageKind imageKind);
}

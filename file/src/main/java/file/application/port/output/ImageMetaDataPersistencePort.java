package file.application.port.output;

import file.domain.ImageMetaData;

import java.util.Optional;

public interface ImageMetaDataPersistencePort {
    ImageMetaData save(ImageMetaData metaData);
    boolean existsById(String imageId);
    Optional<ImageMetaData> findById(String imageId);
    ImageMetaData deleteById(String imageId);
}

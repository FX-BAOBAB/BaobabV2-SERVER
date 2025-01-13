package file.application.port.output;

import file.domain.ImageMetaData;

public interface ImageMetaDataPersistencePort {
    ImageMetaData save(ImageMetaData metaData);
    boolean existsById(String imageId);
    ImageMetaData deleteById(String imageId);
}

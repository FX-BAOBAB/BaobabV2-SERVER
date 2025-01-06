package file.application.port.output;

import file.domain.ImageMetaData;

public interface ImagePersistencePort {
    ImageMetaData save(ImageMetaData metaData);
}

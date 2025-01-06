package file.application.port.output;

import file.domain.ImageMetaData;

public interface ImageMetaDataRepository {
    ImageMetaData save(ImageMetaData metaData);
}

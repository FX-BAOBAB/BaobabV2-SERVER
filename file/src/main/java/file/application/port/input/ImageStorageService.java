package file.application.port.input;

import file.domain.ImageMetaData;
import file.domain.ImageRequest;

public interface ImageStorageService {
    ImageMetaData saveImage(ImageRequest imageRequest);
}

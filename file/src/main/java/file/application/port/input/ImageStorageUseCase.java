package file.application.port.input;

import file.domain.ImageMetaData;
import file.domain.ImageCommand;

public interface ImageStorageUseCase {
    ImageMetaData saveImage(ImageCommand imageCommand);
}

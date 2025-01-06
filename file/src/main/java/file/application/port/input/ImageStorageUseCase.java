package file.application.port.input;

import file.domain.ImageMetaData;
import file.domain.ImageCommand;

import java.util.concurrent.CompletableFuture;

public interface ImageStorageUseCase {
    CompletableFuture<ImageMetaData> saveImage(ImageCommand imageCommand);
}

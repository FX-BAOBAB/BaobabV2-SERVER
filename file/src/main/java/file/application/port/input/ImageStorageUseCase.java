package file.application.port.input;

import file.domain.ImageListCommand;
import file.domain.ImageMetaData;
import file.domain.ImageCommand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ImageStorageUseCase {
    CompletableFuture<ImageMetaData> saveImage(ImageCommand imageCommand);
    CompletableFuture<List<ImageMetaData>> saveImageList(ImageListCommand imageListCommand);
}

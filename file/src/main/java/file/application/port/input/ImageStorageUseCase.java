package file.application.port.input;

import file.domain.ImageMetaData;
import file.domain.ImageCommand;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ImageStorageUseCase {
    CompletableFuture<ImageMetaData> saveImage(ImageCommand imageCommand);
    @Async
    CompletableFuture<List<ImageMetaData>> saveImageList(List<ImageCommand> imageCommandList);
    CompletableFuture<ImageMetaData> updateImage(ImageCommand imageCommand);
    CompletableFuture<List<ImageMetaData>> updateImageList(List<ImageCommand> imageCommandList);
    void deleteImage(String imageId);
}

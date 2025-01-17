package file.application.port.input;

import file.domain.ImageMetaData;
import file.domain.ImageCommand;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ImageStorageUseCase {
    CompletableFuture<ImageMetaData> saveImage(ImageCommand imageCommand);

    CompletableFuture<List<ImageMetaData>> saveImageList(List<ImageCommand> imageCommandList);

    CompletableFuture<ImageMetaData> updateImage(ImageCommand imageCommand);

    CompletableFuture<List<ImageMetaData>> updateImageList(List<ImageCommand> imageCommandList);

    void deleteImage(String imageId);

    void deleteImageList(List<String> imageId);

    CompletableFuture<String> findImageUrl(String imageId);

    List<CompletableFuture<String>> findImageUrlList(List<String> imageId);
}

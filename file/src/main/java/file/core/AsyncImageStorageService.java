package file.core;

import file.application.port.input.ImageStorageUseCase;
import file.domain.ImageCommand;
import file.domain.ImageMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Async
@Service
@RequiredArgsConstructor
public class AsyncImageStorageService implements ImageStorageUseCase {
    private final LocalFileAndDBImageStorageService localFileAndDBImageStorageService;

    @Override
    public CompletableFuture<ImageMetaData> saveImage(ImageCommand imageCommand) {
        ImageMetaData imageMetaData = localFileAndDBImageStorageService.saveImage(imageCommand);
        return CompletableFuture.completedFuture(imageMetaData);
    }

    @Override
    public CompletableFuture<List<ImageMetaData>> saveImageList(List<ImageCommand> imageCommandList) {
        List<ImageMetaData> metaDataList = localFileAndDBImageStorageService.saveImageList(imageCommandList);
        return CompletableFuture.completedFuture(metaDataList);
    }

    @Override
    public CompletableFuture<ImageMetaData> updateImage(ImageCommand imageCommand) {
        ImageMetaData imageMetaData = localFileAndDBImageStorageService.updateImage(imageCommand);
        return CompletableFuture.completedFuture(imageMetaData);
    }

    @Override
    public CompletableFuture<List<ImageMetaData>> updateImageList(List<ImageCommand> imageCommandList) {
        List<ImageMetaData> metaDataList = localFileAndDBImageStorageService.updateImageList(imageCommandList);
        return CompletableFuture.completedFuture(metaDataList);
    }

    @Override
    public void deleteImage(String imageId) {
        localFileAndDBImageStorageService.deleteImage(imageId);
    }

    @Override
    public void deleteImageList(List<String> imageId) {
        localFileAndDBImageStorageService.deleteImageList(imageId);
    }

    @Override
    public CompletableFuture<String> findImageUrl(String imageId) {
        String imageUrl = localFileAndDBImageStorageService.findImageUrl(imageId);
        return CompletableFuture.completedFuture(imageUrl);
    }

    @Override
    public CompletableFuture<List<String>> findImageUrlList(List<String> imageId) {
        List<String> imageUrlList = localFileAndDBImageStorageService.findImageUrlList(imageId);
        return CompletableFuture.completedFuture(imageUrlList);
    }
}

package file.core;

import file.application.port.input.ImageStorageUseCase;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageMetaData;
import file.domain.ImageCommand;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class LocalFileAndDBImageStorageService implements ImageStorageUseCase {
    private final LocalFileStorageService fileStorageService;
    private final MongoDBImageMetaDataService imageMetaDataService;
    private final ApplicationContext context;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Async
    @Transactional
    @Override
    public CompletableFuture<ImageMetaData> saveImage(ImageCommand imageCommand) {

        if (imageCommand.getFile().isEmpty())
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);

        Path filePath = fileStorageService.uploadImage(imageCommand.getFile());
        ImageMetaData metaData = imageMetaDataService.saveImage(imageCommand, filePath);

        return CompletableFuture.completedFuture(metaData);
    }

    @Async
    @Override
    public CompletableFuture<List<ImageMetaData>> saveImageList(List<ImageCommand> imageCommandList) {
        List<CompletableFuture<ImageMetaData>> futures = imageCommandList.stream()
                .map(imageCommand -> getBeanImageStorageUseCase().saveImage(imageCommand))
                .toList();

        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allDone.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .toList()
        );
    }

    @Override
    public CompletableFuture<ImageMetaData> updateImage(ImageCommand imageCommand) {
        return getBeanImageStorageUseCase().saveImage(imageCommand);
    }

    @Override
    public CompletableFuture<List<ImageMetaData>> updateImageList(List<ImageCommand> imageCommandList) {
        return getBeanImageStorageUseCase().saveImageList(imageCommandList);
    }

    @Async
    @Override
    public void deleteImage(String imageId) {

        if (imageId == null || imageId.isEmpty())
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);

        String imageUrl = imageMetaDataService.deleteImage(imageId);
        Path path = Path.of(URI.create(imageUrl).getPath());
        Path filePath = Path.of(uploadDir, path.getFileName().toString());
        fileStorageService.deleteImage(filePath);
    }

    @Override
    public void deleteImageList(List<String> imageId) {
        imageId.forEach(id ->
                CompletableFuture.runAsync(() -> getBeanImageStorageUseCase().deleteImage(id)));
    }

    private ImageStorageUseCase getBeanImageStorageUseCase() {
        return context.getBean(ImageStorageUseCase.class);
    }
}

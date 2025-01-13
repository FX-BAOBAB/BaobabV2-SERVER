package file.core;

import file.application.port.input.ImageStorageUseCase;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageMetaData;
import file.domain.ImageCommand;
import global.utils.ImageIdUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Import(ImageIdUtils.class)
public class LocalFileAndDBImageStorageService implements ImageStorageUseCase {
    private final LocalFileStorageService fileStorageService;
    private final MongoDBImageMetaDataService imageMetaDataService;
    private final ApplicationContext context;
    private final ImageIdUtils imageIdUtils;

    @Async
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
                .map(imageCommand -> {
                    ImageStorageUseCase imageStorageUseCase = context.getBean(ImageStorageUseCase.class);
                    return imageStorageUseCase.saveImage(imageCommand);
                })
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
    public void deleteImage(String encodeImageId) {
        String imageId = imageIdUtils.decodeImageId(encodeImageId);

        if (imageId.isEmpty())
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR, "Image id is empty");

        String imageUrl = imageMetaDataService.deleteImage(imageId);
        fileStorageService.deleteImage(Path.of(URI.create(imageUrl).getPath()));
    }

    private ImageStorageUseCase getBeanImageStorageUseCase() {
        return context.getBean(ImageStorageUseCase.class);
    }
}

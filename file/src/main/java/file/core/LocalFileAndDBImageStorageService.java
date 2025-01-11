package file.core;

import file.application.port.input.ImageStorageUseCase;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageMetaData;
import file.domain.ImageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class LocalFileAndDBImageStorageService implements ImageStorageUseCase {
    private final LocalFileStorageService fileStorageService;
    private final MongoDBImageMetaDataService imageMetaDataService;
    private final ApplicationContext context;

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

    private ImageStorageUseCase getBeanImageStorageUseCase() {
        return context.getBean(ImageStorageUseCase.class);
    }
}

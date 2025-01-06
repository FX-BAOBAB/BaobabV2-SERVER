package file.core;

import file.application.port.input.ImageStorageUseCase;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageMetaData;
import file.domain.ImageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class LocalFileAndDBImageStorageService implements ImageStorageUseCase {
    private final LocalFileStorageService fileStorageService;
    private final MongoDBImageMetaDataService imageMetaDataService;

    @Async
    @Override
    public CompletableFuture<ImageMetaData> saveImage(ImageCommand imageCommand) {
        if (imageCommand.getFile().isEmpty())
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);

        Path filePath = fileStorageService.uploadImage(imageCommand.getFile());
        ImageMetaData metaData = imageMetaDataService.saveImage(imageCommand, filePath);

        return CompletableFuture.completedFuture(metaData);
    }
}

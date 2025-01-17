package file.core;

import file.application.port.output.ImageMetaDataPersistencePort;
import file.core.common.utils.FileUtils;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageNotFoundException;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageCommand;
import file.domain.ImageMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MongoDBImageMetaDataService {

    @Value("${file.context-path}")
    private String contextPath;
    private final ImageMetaDataPersistencePort imageMetaDataPersistencePort;

    public ImageMetaData saveImageMetaData(ImageCommand imageCommand, Path filePath) {
        ImageMetaData imageMetaData = createImageMetaData(imageCommand, filePath);
        return imageMetaDataPersistencePort.save(imageMetaData);
    }

    public ImageMetaData deleteImageMetaData(String imageId) {
        ImageMetaData imageMetaData = findImageMetaData(imageId);
        imageMetaDataPersistencePort.deleteById(imageId);

        if(imageMetaDataPersistencePort.existsById(imageId)) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        return imageMetaData;
    }

    public ImageMetaData findImageMetaData(String imageId) {
        return imageMetaDataPersistencePort.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND));
    }

    private ImageMetaData createImageMetaData(ImageCommand imageCommand, Path filePath) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(imageCommand.getFile().getOriginalFilename()));
        String fileName = filePath.getFileName().toString();

        return ImageMetaData.builder()
                .id(imageCommand.getId())
                .url(createImageUrl(filePath))
                .originalName(FileUtils.getFileOfName(originalFileName))
                .serverName(FileUtils.getFileOfName(fileName))
                .extension(FileUtils.getExtension(fileName))
                .kind(imageCommand.getKind())
                .build();
    }

    private String createImageUrl(Path filePath) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .scheme("http")
                .path(contextPath + "/" + filePath.getFileName())
                .toUriString();
    }
}

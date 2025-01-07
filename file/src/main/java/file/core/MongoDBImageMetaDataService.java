package file.core;

import file.application.port.output.ImageMetaDataPersistencePort;
import file.application.port.output.utils.FileUtils;
import file.domain.ImageCommand;
import file.domain.ImageMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MongoDBImageMetaDataService {
    private final ImageMetaDataPersistencePort imageMetaDataPersistencePort;

    public ImageMetaData saveImage(ImageCommand imageCommand, Path filePath) {
        return imageMetaDataPersistencePort.save(createImageMetaData(imageCommand, filePath));
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
                .scheme("https")
                .path(filePath.toString())
                .toUriString();
    }
}

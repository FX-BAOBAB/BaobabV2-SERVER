package file.core;

import file.application.port.output.ImageMetaDataPersistencePort;
import file.core.common.utils.FileUtils;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongoDBImageMetaDataService {

    @Value("${url.host}")
    private String host;
    @Value("${url.scheme}")
    private String scheme;
    @Value("${file.context-path}")
    private String contextPath;
    @Value("${url.path.user}")
    private String userApiPath;
    @Value("${url.path.article}")
    private String articleApiPath;
    private final ImageMetaDataPersistencePort imageMetaDataPersistencePort;

    public ImageMetaData saveImageMetaData(ImageCommand imageCommand, Path filePath) {
        ImageMetaData imageMetaData = createImageMetaData(imageCommand, filePath);
        return imageMetaDataPersistencePort.save(imageMetaData);
    }

    public ImageMetaData deleteImageMetaData(String imageId) {
        Optional<ImageMetaData> imageMetaData = imageMetaDataPersistencePort.findById(imageId);
        if (imageMetaData.isEmpty()) {
            log.warn("Image metadata not found for id: {}", imageId);
            return null;
        }

        imageMetaDataPersistencePort.deleteById(imageId);

        if(imageMetaDataPersistencePort.existsById(imageId)) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_DELETE_ERROR);
        }

        log.info("Image MetaData deleted successfully {}", imageMetaData.get());

        return imageMetaData.get();
    }

    public ImageMetaData findImageMetaData(String imageId) {
        return imageMetaDataPersistencePort.findById(imageId)
                .orElseThrow(() -> new ImageStorageException(ImageErrorCode.IMAGE_NOT_FOUND));
    }

    private ImageMetaData createImageMetaData(ImageCommand imageCommand, Path filePath) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(imageCommand.getFile().getOriginalFilename()));
        String fileName = filePath.getFileName().toString();

        return ImageMetaData.builder()
                .id(imageCommand.getId())
                .url(createImageUrl(filePath, imageCommand.getKind()))
                .originalName(FileUtils.getFileOfName(originalFileName))
                .serverName(FileUtils.getFileOfName(fileName))
                .extension(FileUtils.getExtension(fileName))
                .kind(imageCommand.getKind())
                .build();
    }

    private String createImageUrl(Path filePath, ImageKind kind) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .path(resolvePathForImageKind(kind) + contextPath + filePath.getFileName())
                .toUriString();
    }

    private String resolvePathForImageKind(ImageKind imageKind) {
        if (imageKind == ImageKind.USER)
            return userApiPath;
        return articleApiPath;
    }
}

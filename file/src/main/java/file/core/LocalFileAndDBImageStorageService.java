package file.core;

import file.application.port.input.ImageStorageUseCase;
import file.application.port.output.FileStorage;
import file.application.port.output.ImageMetaDataRepository;
import file.application.port.output.utils.FileUtils;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageMetaData;
import file.domain.ImageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class LocalFileAndDBImageStorageService implements ImageStorageUseCase {
    private final FileStorage fileStorage;
    private final ImageMetaDataRepository imageMetaDataRepository;

    @Value("${file.upload-dir}")
    private String dirPath;

    @Override
    public ImageMetaData saveImage(ImageCommand imageCommand) {
        if (imageCommand.getFile().isEmpty())
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);

        String serverName = fileStorage.store(imageCommand.getFile());
        ImageMetaData metaData = createImageMetaData(imageCommand, serverName);

        return imageMetaDataRepository.save(metaData);
    }

    private ImageMetaData createImageMetaData(ImageCommand imageCommand, String serverName) {
        String originalFilename = imageCommand.getFile().getOriginalFilename();
        String extension = FileUtils.extractFileExtension(originalFilename);

        String url = createImageUrl(serverName, extension);

        return ImageMetaData.builder()
                .url(url)
                .originalName(originalFilename)
                .serverName(serverName)
                .kind(imageCommand.getKind())
                .build();
    }

    private String createImageUrl(String serverName, String extension) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .scheme("https")
                .path(dirPath + serverName + extension)
                .toUriString();
    }
}

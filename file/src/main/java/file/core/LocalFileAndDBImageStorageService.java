package file.core;

import file.application.port.input.ImageStorageService;
import file.application.port.output.FileStorage;
import file.application.port.output.ImageMetaDataRepository;
import file.application.port.output.utils.FileUtils;
import file.domain.ImageMetaData;
import file.domain.ImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class LocalFileAndDBImageStorageService implements ImageStorageService {
    private final FileStorage fileStorage;
    private final ImageMetaDataRepository imageMetaDataRepository;

    @Value("${file.upload-dir}")
    private String dirPath;

    @Override
    public ImageMetaData saveImage(ImageRequest imageRequest) {
        String serverName = fileStorage.store(imageRequest.getImageFile());
        ImageMetaData metaData = createImageMetaData(imageRequest, serverName);

        return imageMetaDataRepository.save(metaData);
    }

    private ImageMetaData createImageMetaData(ImageRequest imageRequest, String serverName) {
        String originalFilename = imageRequest.getImageFile().getOriginalFilename();
        String extension = FileUtils.extractFileExtension(originalFilename);

        String url = createImageUrl(serverName, extension);

        return ImageMetaData.builder()
                .url(url)
                .originalName(originalFilename)
                .serverName(serverName)
                .kind(imageRequest.getKind())
                .build();
    }

    private String createImageUrl(String serverName, String extension) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .scheme("https")
                .path(dirPath + serverName + extension)
                .toUriString();
    }
}

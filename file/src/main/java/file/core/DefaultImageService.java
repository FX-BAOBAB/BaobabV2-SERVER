package file.core;

import file.application.port.input.DefaultImageUseCase;
import file.application.port.input.ImageMetaDataUseCase;
import file.application.port.input.ImageStorageUseCase;
import file.application.port.output.ImageMetaDataPersistencePort;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DefaultImageService implements DefaultImageUseCase {

    private final ImageMetaDataPersistencePort imageMetaDataPersistencePort;

    private final ImageMetaDataUseCase imageMetaDataUseCase;
    private final ImageStorageUseCase imageStorageUseCase;

    /**
     * Default Image 가 이미 존재하는 경우, 기존 이미지를 새로운 이미지로 대체
     * @param kind
     * @param userId
     * @param file
     * @return
     */
    @Override
    public ImageMetaData saveDefaultImage(ImageKind kind, String userId, MultipartFile file) {
        imageMetaDataPersistencePort.findFirstByImageKind(kind)
            .ifPresent(existingImage -> imageStorageUseCase.deleteImage(existingImage.getId()));

        return imageMetaDataUseCase.processImageMetaData(kind, userId, file);
    }

    @Override
    public ImageMetaData getDefaultImage(ImageKind imageKind) {
        return imageMetaDataPersistencePort.findFirstByImageKind(imageKind)
            .orElseThrow(() -> new ImageStorageException(ImageErrorCode.IMAGE_NOT_FOUND));
    }

}

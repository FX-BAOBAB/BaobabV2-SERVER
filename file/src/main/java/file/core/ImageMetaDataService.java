package file.core;

import file.application.port.input.ImageMetaDataUseCase;
import file.application.port.input.ImageStorageUseCase;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import global.errorcode.ErrorCode;
import global.utils.ImageIdUtils;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageMetaDataService implements ImageMetaDataUseCase {

    private final ImageIdUtils imageIdUtils;

    private final ImageStorageUseCase imageStorageUseCase;

    @Override
    public ImageMetaData processImageMetaData(ImageKind imageKind, String userId, MultipartFile image) {

        ImageCommand imageCommand = generateImageCommand(imageKind, userId, image);

        try {
            return imageStorageUseCase.saveImage(imageCommand).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // interrupt 플래그를 설정
            throw new RuntimeException(ErrorCode.ASYNC_ERROR.getDescription(), e);
        } catch (ExecutionException e) {
            throw new RuntimeException(ErrorCode.DB_PROCESS_ERROR.getDescription(), e);
        }
    }

    @Override
    public List<ImageMetaData> processImageMetaDataList(
        ImageKind imageKind, String userId, List<MultipartFile> imageList) {

        return imageList.stream()
            .map(image -> processImageMetaData(imageKind, userId, image))
            .toList();
    }

    private ImageCommand generateImageCommand(ImageKind imageKind, String userId, MultipartFile image) {
        String imageId = imageIdUtils.generateImageId(imageKind.getDescription(), userId);
        return ImageCommand.builder()
            .id(imageId)
            .file(image)
            .kind(imageKind)
            .build();
    }

}

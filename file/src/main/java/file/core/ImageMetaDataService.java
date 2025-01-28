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
    public ImageMetaData processImageMetaData(String module, String userId, MultipartFile image) {

        ImageCommand imageCommand = generateImageCommand(module, userId, image);

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
        String module, String userId, List<MultipartFile> imageList) {

        return imageList.stream()
            .map(image -> processImageMetaData(module, userId, image))
            .toList();
    }

    private ImageCommand generateImageCommand(String module, String userId, MultipartFile image) {
        String imageId = imageIdUtils.generateImageId(module, userId);
        return ImageCommand.builder()
            .id(imageId)
            .file(image)
            .kind(ImageKind.valueOf(module))
            .build();
    }

}

package file.application.port.input;

import file.domain.ImageKind;
import file.domain.ImageMetaData;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageMetaDataUseCase {

    ImageMetaData processImageMetaData(ImageKind imageKind, String userId, MultipartFile image);

    List<ImageMetaData> processImageMetaDataList(
        ImageKind imageKind, String userId, List<MultipartFile> imageList);

}

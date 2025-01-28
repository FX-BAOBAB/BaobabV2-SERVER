package file.application.port.input;

import file.domain.ImageMetaData;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageMetaDataUseCase {

    ImageMetaData processImageMetaData(String module, String userId, MultipartFile image);

    List<ImageMetaData> processImageMetaDataList(
        String module, String userId, List<MultipartFile> imageList);

}

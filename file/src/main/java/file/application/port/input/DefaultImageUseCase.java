package file.application.port.input;

import file.domain.ImageKind;
import file.domain.ImageMetaData;
import org.springframework.web.multipart.MultipartFile;

public interface DefaultImageUseCase {

    ImageMetaData saveDefaultImage(ImageKind kind, String userId, MultipartFile file);

    ImageMetaData getDefaultImage(ImageKind imageKind);

}

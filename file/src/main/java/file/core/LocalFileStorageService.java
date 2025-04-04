package file.core;

import file.application.port.output.FileDirStoragePort;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService {

    private final FileDirStoragePort filedirStoragePort;

    public Path uploadImage(MultipartFile file) {
        return filedirStoragePort.resizeAndStore(file);
    }

    public void deleteImage(Path filePath) {
        filedirStoragePort.delete(filePath);
    }

    public void checkIfExistImage(Path filePath) {
        if (!filedirStoragePort.isExist(filePath)) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_NOT_FOUND);
        }
    }
}

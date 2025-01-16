package file.application.port.output;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileDirStoragePort {
    Path store(MultipartFile imageFile);
    void delete(Path filePath);
    boolean isExist(Path filePath);
}

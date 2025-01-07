package file.application.port.output;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileDirStorage {
    void store(Path filePath,MultipartFile imageFile);
}

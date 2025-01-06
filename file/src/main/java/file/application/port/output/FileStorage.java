package file.application.port.output;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorage {
    void store(MultipartFile imageFile, Path filePath);
}

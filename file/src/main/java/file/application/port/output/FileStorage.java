package file.application.port.output;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    String store(MultipartFile file);
}

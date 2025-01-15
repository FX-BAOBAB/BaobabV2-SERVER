package file.core;

import file.application.port.output.FileDirStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService {

    private final FileDirStoragePort filedirStoragePort;

    public Path uploadImage(MultipartFile file) {
        return filedirStoragePort.store(file);
    }

    public void deleteImage(Path filePath) {
        filedirStoragePort.delete(filePath);
    }
}

package file.core;

import file.application.port.output.FileStorage;
import file.application.port.output.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService {
    private final FileStorage fileStorage;
    @Value("${file.upload-dir}")
    private String dirPath;

    public Path uploadImage(MultipartFile file) {
        Path filePath = createFilePath(file);
        fileStorage.store(file, filePath);
        return filePath;
    }

    private Path createFilePath(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = FileUtils.getExtension(fileName);
        String serverName = UUID.randomUUID().toString();

        return Paths.get(dirPath, serverName + extension);
    }
}

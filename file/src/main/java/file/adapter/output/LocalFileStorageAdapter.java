package file.adapter.output;

import file.application.port.output.FileDirStoragePort;
import file.core.common.utils.FileUtils;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import global.annotation.output.PersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@PersistenceAdapter
@Slf4j
public class LocalFileStorageAdapter implements FileDirStoragePort {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public Path store(MultipartFile imageFile) {
        createDirectory(uploadDir);
        Path filePath = createFilePath(imageFile);

        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        } catch (Exception e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR, e);
        } finally {
            logImageUploadStatus(filePath);
        }

        return filePath;
    }

    private void logImageUploadStatus(Path filePath) {
        if (Files.exists(filePath)) {
            log.info("Image uploaded successfully to {}", filePath);
        } else {
            log.error("Failed to upload image to {}", filePath);
        }
    }

    private void createDirectory(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_PATH_ERROR);
        }
    }

    private Path createFilePath(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = FileUtils.getExtension(fileName);
        String serverName = UUID.randomUUID().toString();

        return Paths.get(uploadDir, serverName + extension);
    }

    @Override
    public void delete(Path filePath) {
        File imageFile = new File(filePath.toString());

        if (!imageFile.exists()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_NOT_FOUND);
        }

        if (!imageFile.delete()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        log.info("Image deleted successfully at {}", filePath);
    }

    @Override
    public boolean isExist(Path filePath) {
        return Files.exists(filePath);
    }
}

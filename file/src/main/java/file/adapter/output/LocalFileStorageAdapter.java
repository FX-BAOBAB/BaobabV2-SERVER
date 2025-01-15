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
    private String dirPath;

    @Override
    public Path store(MultipartFile imageFile) {
        createDirectory(dirPath);
        Path filePath = createFilePath(imageFile);

        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        } catch (Exception e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR, e);
        } finally {
            if (Files.exists(filePath)) {
                log.info("Uploaded image to {}", filePath);
            } else {
                log.error("Image upload failed, file not found at: {}", filePath);
            }
        }

        return filePath;
    }

    private void createDirectory(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_PATH_ERROR);
            }
        }
    }

    private Path createFilePath(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = FileUtils.getExtension(fileName);
        String serverName = UUID.randomUUID().toString();

        return Paths.get(dirPath, serverName + extension);
    }

    @Override
    public void delete(Path filePath) {
        File imageFile = new File(filePath.toString());

        if (!imageFile.exists()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_NOT_FOUND);
        }

        try {
            if (!imageFile.delete()) {
                throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
            }
        } catch (Exception e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR, e);
        }

        log.info("Deleted image at: {}", filePath);
    }
}

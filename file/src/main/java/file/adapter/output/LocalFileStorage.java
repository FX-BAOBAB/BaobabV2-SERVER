package file.adapter.output;

import file.application.port.output.FileDirStorage;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Repository
@Slf4j
public class LocalFileStorage implements FileDirStorage {

    @Override
    public void store(Path filePath, MultipartFile imageFile) {

        File directory = new File(filePath.getParent().toString());
        if (!directory.exists()) {
            boolean mkDirs = directory.mkdirs();
            if (!mkDirs) {
                throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_PATH_ERROR);
            }
        }

        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        } finally {
            if (Files.exists(filePath)) {
                log.info("File exists at: {}", filePath);
            } else {
                log.error("Image upload failed, file not found at: {}", filePath);
            }
        }
        log.info("Uploaded image to {}", filePath);
    }
}

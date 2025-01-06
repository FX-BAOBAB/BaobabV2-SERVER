package file.adapter.output;

import file.application.port.output.FileStorage;
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
public class LocalFileStorage implements FileStorage {
    @Override
    public void store(MultipartFile imageFile, Path filePath) {
        File directory = new File(filePath.getParent().toString());
        if (!directory.exists()) directory.mkdirs();

        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        log.info("Uploaded image to {}", filePath);
    }
}

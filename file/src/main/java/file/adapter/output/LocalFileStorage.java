package file.adapter.output;

import file.application.port.output.FileStorage;
import file.application.port.output.utils.FileUtils;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Repository
@Slf4j
public class LocalFileStorage implements FileStorage {
    @Value("${file.upload-dir}")
    private String dirPath;

    @Override
    public String store(MultipartFile imageFile) {
        File directory = new File(dirPath);
        if (!directory.exists()) directory.mkdirs();

        String extension = FileUtils.extractFileExtension(imageFile.getOriginalFilename());
        String serverName = UUID.randomUUID().toString();

        Path filePath = Paths.get(dirPath, serverName + extension);

        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        log.info("Uploaded image to {}", filePath);

        return serverName;
    }
}

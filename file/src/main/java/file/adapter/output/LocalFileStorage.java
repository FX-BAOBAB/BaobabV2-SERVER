package file.adapter.output;

import file.adapter.output.exception.FileStorageException;
import file.application.port.output.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
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

@Repository
@Slf4j
public class LocalFileStorage implements FileStorage {
    @Value("${file.upload-dir}")
    private String dirPath;

    @Override
    public String store(MultipartFile imageFile) {
        File directory = new File(dirPath);
        if (!directory.exists()) directory.mkdirs();

        String originalFileName = imageFile.getOriginalFilename();
        String cleanedFileName = StringUtils.cleanPath(Objects.requireNonNull(originalFileName));
        String extension = originalFileName.substring(cleanedFileName.lastIndexOf("."));
        String serverName = UUID.randomUUID().toString();

        Path filePath = Paths.get(dirPath, serverName + extension);

        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("이미지 파일 저장에 실패했습니다.");
        }

        log.info("Uploaded image to {}", filePath);

        return serverName;
    }
}

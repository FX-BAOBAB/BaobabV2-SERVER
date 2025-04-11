package file.adapter.output;

import file.application.port.output.FileDirStoragePort;
import file.core.common.utils.FileUtils;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import global.annotation.output.PersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private static final String OUTPUT_FORMAT = "jpg";

    @Override
    public Path store(MultipartFile imageFile) {
        createDirectory(uploadDir);
        Path filePath = createFilePath(imageFile);

        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_UPLOAD_ERROR, e);
        } finally {
            logImageUploadStatus(filePath);
        }

        return filePath;
    }

    @Override
    public Path resizeAndStore(MultipartFile imageFile) {
        createDirectory(uploadDir);
        Path filePath = createFilePath(imageFile);

        // TODO: usdz 확장자 불필요 시 코드 수정 필요
        // 파일이 usdz 확장자일 시 jpg 포맷 설정 제외
        try {
            if (FileUtils.getExtension(filePath.getFileName().toString()).equals(".usdz")) {
                Thumbnails.of(imageFile.getInputStream())
                        .size(600, 600)
                        .outputFormat(OUTPUT_FORMAT)
                        .outputQuality(0.8)
                        .toFile(new File(filePath.toString()));
            } else {
                Thumbnails.of(imageFile.getInputStream())
                        .size(600, 600)
                        .outputQuality(0.8)
                        .toFile(new File(filePath.toString()));
            }
        } catch (Exception e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_UPLOAD_ERROR, e);
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
            throw new ImageStorageException(ImageErrorCode.IMAGE_DIRECTORY_ERROR);
        }
    }

    private Path createFilePath(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // TODO: usdz 확장자 불필요 시 코드 수정 필요
        String extension = FileUtils.getExtension(fileName);

        // usdz 확장자 압축 포맷 변경 제외
        if (!extension.equals(".usdz"))
            extension = "." + OUTPUT_FORMAT;

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
            throw new ImageStorageException(ImageErrorCode.IMAGE_DELETE_ERROR);
        }

        log.info("Image deleted successfully at {}", filePath);
    }

    @Override
    public boolean isExist(Path filePath) {
        return Files.exists(filePath);
    }
}

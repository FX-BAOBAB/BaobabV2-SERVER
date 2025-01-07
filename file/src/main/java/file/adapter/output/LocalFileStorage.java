package file.adapter.output;

import file.application.port.output.FileDirStorage;
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
import java.nio.file.StandardCopyOption;

@Repository
@Slf4j
public class LocalFileStorage implements FileDirStorage {


    @Override
    public void store(Path filePath, MultipartFile imageFile) {

        File directory = new File(filePath.getParent().toString());
        if (!directory.exists()) {
            boolean mkDirs = directory.mkdirs();
            // TODO Exception 처리 필요
            if (!mkDirs) {
                throw new RuntimeException("DIR 생성 실패!");
            }
        }

        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }finally {
            // TODO Image Upload Logging 필요, 실패 시 실패 사유 분석 용
        }

        log.info("Uploaded image to {}", filePath);
    }
}

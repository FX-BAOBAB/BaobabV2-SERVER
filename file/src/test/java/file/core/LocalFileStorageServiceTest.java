package file.core;

import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class LocalFileStorageServiceTest {
    @Autowired
    private LocalFileStorageService localFileStorageService;
    @Mock
    private MultipartFile imageFile;
    @Value("${file.upload-dir}")
    private String dirPath;

    @AfterEach
    void tearDown() throws IOException {
        Path directoryPath = Paths.get(dirPath);
        if (Files.exists(directoryPath)) {
            Files.walk(directoryPath)
                    .map(Path::toFile)
                    .forEach(File::delete);
            Files.deleteIfExists(directoryPath);
        }
    }

    @Test
    void 이미지_업로드_성공하면_filePath를_반환한다() throws IOException {
        when(imageFile.getOriginalFilename()).thenReturn("testImage1.jpg");
        InputStream inputStream = new ByteArrayInputStream("dummy image content".getBytes());
        when(imageFile.getInputStream()).thenReturn(inputStream);

        Path uploadedPath = localFileStorageService.uploadImage(imageFile);

        assertTrue(Files.exists(uploadedPath));

        String fileContent = Files.readString(uploadedPath);
        assertEquals("dummy image content", fileContent);
    }

    @Test
    void 이미지_삭제_성공_테스트() throws IOException {
        when(imageFile.getOriginalFilename()).thenReturn("testImage3.jpg");
        InputStream inputStream = new ByteArrayInputStream("dummy image content".getBytes());
        when(imageFile.getInputStream()).thenReturn(inputStream);
        Path uploadedPath = localFileStorageService.uploadImage(imageFile);

        localFileStorageService.deleteImage(uploadedPath);

        assertFalse(Files.exists(uploadedPath));
    }

    @Test
    void 이미지_삭제_중_파일이_존재하지_않으면_ImageStorageException을_던진다() {
        Path notExistPath = Path.of(dirPath, "sdf.jpg");

        ImageStorageException e = assertThrows(ImageStorageException.class, () ->
                localFileStorageService.deleteImage(notExistPath));

        assertEquals(e.getErrorCodeIfs(), ImageErrorCode.IMAGE_NOT_FOUND);
    }

    @Test
    void 이미지_업로드_실패하면_ImageStorageException을_던진다() throws IOException {
        when(imageFile.getOriginalFilename()).thenReturn("testImage2.jpg");
        when(imageFile.getInputStream()).thenReturn(null);

        ImageStorageException e = assertThrows(ImageStorageException.class, () ->
                localFileStorageService.uploadImage(imageFile));
        assertEquals(e.getErrorCodeIfs(), ImageErrorCode.IMAGE_STORAGE_ERROR);
    }

    @Test
    void 이미지_삭제_성공_테스트() throws IOException {
        when(imageFile.getOriginalFilename()).thenReturn("testImage3.jpg");
        InputStream inputStream = new ByteArrayInputStream("dummy image content".getBytes());
        when(imageFile.getInputStream()).thenReturn(inputStream);
        Path uploadedPath = localFileStorageService.uploadImage(imageFile);

        localFileStorageService.deleteImage(uploadedPath);
        assertFalse(Files.exists(uploadedPath));
    }

    @Test
    void 이미지_삭제_중_파일이_존재하지_않으면_ImageStorageException을_던진다() {
        Path notExistPath = Path.of(dirPath, "sdf.jpg");

        ImageStorageException e = assertThrows(ImageStorageException.class, () ->
                localFileStorageService.deleteImage(notExistPath));

        assertEquals(e.getErrorCodeIfs(), ImageErrorCode.IMAGE_NOT_FOUND);
    }
}
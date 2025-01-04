package file.adapter.output;

import file.adapter.output.exception.FileStorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LocalFileStorageTest {
    @Autowired
    private LocalFileStorage localFileStorage;
    @Mock
    private MultipartFile imageFile;
    private final String testUploadDir = "test-upload-dir";

    @BeforeEach
    void setUp() throws IOException {
        ReflectionTestUtils.setField(localFileStorage, "dirPath", testUploadDir);

        when(imageFile.getOriginalFilename()).thenReturn("testImage.jpg");
        InputStream inputStream = new ByteArrayInputStream("dummy image content".getBytes());
        when(imageFile.getInputStream()).thenReturn(inputStream);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path directoryPath = Paths.get(testUploadDir);
        if (Files.exists(directoryPath)) {
            Files.walk(directoryPath)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    void 이미지_파일스토리지_저장_후_서버이름을_반환해야_한다() throws IOException {
        String serverName = localFileStorage.store(imageFile);

        Path savedFilePath = Paths.get(testUploadDir, serverName + ".jpg");
        assertTrue(Files.exists(savedFilePath));

        String fileContent = Files.readString(savedFilePath);
        assertEquals("dummy image content", fileContent);
    }

    @Test
    void 이미지_파일스토리지_저장_중_실패하면_예외가_발생해야_한다(){
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.copy(any(InputStream.class), any(Path.class), any(StandardCopyOption.class)))
                    .thenThrow(new IOException());

            FileStorageException exception = assertThrows(FileStorageException.class, () ->
                localFileStorage.store(imageFile));

            assertEquals("이미지 파일 저장에 실패했습니다.", exception.getMessage());
        }
    }
}
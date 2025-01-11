package file.core;

import file.application.port.input.ImageStorageUseCase;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class LocalFileAndDBImageStorageServiceTest {
    @Autowired
    private ImageStorageUseCase localFileAndDBImageStorageService;
    @Mock
    private MultipartFile imageFile;
    @Value("${file.upload-dir}")
    private String dirPath;

    @BeforeEach
    void setUp() throws IOException {
        when(imageFile.getOriginalFilename()).thenReturn("test1.jpg");
        InputStream inputStream = new ByteArrayInputStream("dummy image content".getBytes());
        when(imageFile.getInputStream()).thenReturn(inputStream);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path directoryPath = Paths.get(dirPath);
        if (Files.exists(directoryPath)) {
            Files.walk(directoryPath)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    void 이미지_수정_성공하면_수정된_ImageMetaData를_반환한다() throws ExecutionException, InterruptedException {
        ImageCommand oldCommand = ImageCommand.builder()
                .id("image_4")
                .file(imageFile)
                .kind(ImageKind.USER)
                .build();
        ImageCommand newCommand = ImageCommand.builder()
                .id("image_5")
                .file(imageFile)
                .kind(ImageKind.USER)
                .build();

        ImageMetaData savedImageMetaData = localFileAndDBImageStorageService.saveImage(oldCommand).get();
        ImageMetaData updatedImageMetaData = localFileAndDBImageStorageService.updateImage(newCommand).get();

        assertThat(savedImageMetaData.getId()).isNotEqualTo(updatedImageMetaData.getId());
        assertThat(savedImageMetaData.getId()).isEqualTo("image_4");
        assertThat(updatedImageMetaData.getId()).isEqualTo("image_5");
    }
}
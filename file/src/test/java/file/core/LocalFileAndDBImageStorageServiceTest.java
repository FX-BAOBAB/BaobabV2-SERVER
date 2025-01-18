package file.core;

import file.domain.ImageCommand;
import file.domain.ImageMetaData;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import utils.FileTestUtils;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocalFileAndDBImageStorageServiceTest {
    @Autowired
    private LocalFileAndDBImageStorageService localFileAndDBImageStorageService;

    @Value("${file.upload-dir}")
    private String dirPath;

    private static MockedStatic<TransactionSynchronizationManager> transactionManagerMock;


    @AfterEach
    void cleanup() throws IOException {
        Path directoryPath = Paths.get(dirPath);
        if (Files.exists(directoryPath)) {
            Files.walk(directoryPath)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    private ImageMetaData save(ImageCommand imageCommand) {
        transactionManagerMock = Mockito.mockStatic(TransactionSynchronizationManager.class);
        transactionManagerMock.when(TransactionSynchronizationManager::isSynchronizationActive).thenReturn(true);

        ImageMetaData savedImageMetaData = localFileAndDBImageStorageService.saveImage(imageCommand);

        return savedImageMetaData;
    }

    @Test
    void 이미지_저장_성공() {
        MockMultipartFile oldFile = FileTestUtils.makeMockMultipartFile("test.jpg", "test");
        ImageCommand imageCommand = FileTestUtils.makeMockImageCommand("image0", oldFile);

        ImageMetaData imageMetaData = save(imageCommand);
        transactionManagerMock.close(); // Mock 해제

        assertThat(imageMetaData.getId()).isEqualTo("image0");
    }



    @Test
    void 이미지_수정_성공하면_수정된_ImageMetaData를_반환한다() {
        MockMultipartFile oldFile = FileTestUtils.makeMockMultipartFile("oldTest.jpg", "old test");
        ImageCommand oldImageCommand = FileTestUtils.makeMockImageCommand("image1", oldFile);

        ImageMetaData savedImageMetaData = save(oldImageCommand);

        MockMultipartFile newFile = FileTestUtils.makeMockMultipartFile("newTest.jpg", "new test");
        ImageCommand newImageCommand = FileTestUtils.makeMockImageCommand("image1", newFile);
        ImageMetaData updatedImageMetaData = localFileAndDBImageStorageService.updateImage(newImageCommand);
        transactionManagerMock.close(); // Mock 해제

        assertThat(savedImageMetaData.getId()).isEqualTo(updatedImageMetaData.getId());
        assertThat(updatedImageMetaData.getOriginalName()).isEqualTo("newTest");
    }

    @Test
    void 이미지_삭제_성공하면_삭제_이후_조회_시_이미지_파일이_존재하지_않는다() {
        MockMultipartFile file = FileTestUtils.makeMockMultipartFile("test.jpg", "test");
        ImageCommand imageCommand = FileTestUtils.makeMockImageCommand("image2", file);
        ImageMetaData imageMetaData = save(imageCommand);
        transactionManagerMock.close(); // Mock 해제

        localFileAndDBImageStorageService.deleteImage(imageMetaData.getId());

        boolean isExists = Files.exists(Path.of(URI.create(imageMetaData.getUrl()).getPath()));
        assertThat(isExists).isFalse();
    }

    @Test
    void 조회() {
        MockMultipartFile file = FileTestUtils.makeMockMultipartFile("test.jpg", "test");
        ImageCommand imageCommand = FileTestUtils.makeMockImageCommand("image3", file);
        ImageMetaData imageMetaData = save(imageCommand);
        transactionManagerMock.close(); // Mock 해제

        String imageUrl = localFileAndDBImageStorageService.findImageUrl(imageMetaData.getId());

        assertThat(imageUrl).isNotNull();
        assertThat(imageUrl).isEqualTo(imageMetaData.getUrl());
    }
}
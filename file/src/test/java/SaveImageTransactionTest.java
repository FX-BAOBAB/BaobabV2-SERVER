import file.adapter.output.repository.MongoDBImageMetaDataRepository;
import file.core.LocalFileAndDBImageStorageService;
import file.core.LocalFileStorageService;
import file.core.MongoDBImageMetaDataService;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SaveImageTransactionTest {
    @InjectMocks
    private LocalFileAndDBImageStorageService imageService; // 실제 객체

    @Mock
    private LocalFileStorageService fileStorageService; // Mock 객체

    @Mock
    private MongoDBImageMetaDataService imageMetaDataService; // Mock 객체

    @Mock
    private MongoDBImageMetaDataRepository mongoDBImageMetaDataRepository;

    private static MockedStatic<TransactionSynchronizationManager> transactionManagerMock;

    @BeforeEach
    void setup() {
        transactionManagerMock = Mockito.mockStatic(TransactionSynchronizationManager.class);
        transactionManagerMock.when(TransactionSynchronizationManager::isSynchronizationActive).thenReturn(true);
    }

    @AfterEach
    void cleanup() {
        transactionManagerMock.close(); // Mock 해제
    }


    @Test
    void saveImage_transactionalTest() throws Exception {
        // Given
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                "test-content".getBytes()
        );

        ImageCommand imageCommand = ImageCommand.builder()
                .id("image_0869")
                .file(mockFile)
                .kind(ImageKind.USER)
                .build();
        Path mockPath = Path.of("test-path/test-image.jpg");

        // Mock 파일 업로드 동작
        Mockito.when(fileStorageService.uploadImage(mockFile)).thenReturn(mockPath);

        // Mock 메타데이터 저장 동작
        ImageMetaData mockMetaData = ImageMetaData.builder()
                .id("image_0869")
                .url("eddghghdghdgh/test-path/test-image.jpg")
                .serverName("testServerName")
                .originalName("testOriginalName")
                .kind(ImageKind.USER)
                .build();
        Mockito.when(imageMetaDataService.saveImageMetaData(imageCommand, mockPath)).thenReturn(mockMetaData);

        // When
        ImageMetaData savedMetaData = imageService.saveImage(imageCommand);


        // Then
        // Mock 객체의 동작 검증
        Mockito.verify(fileStorageService, Mockito.times(1)).uploadImage(mockFile);
        Mockito.verify(imageMetaDataService, Mockito.times(1)).saveImageMetaData(imageCommand, mockPath);

        // 반환된 메타데이터 검증
        assertNotNull(savedMetaData);
        assertEquals("testServerName", savedMetaData.getServerName());
        assertTrue(savedMetaData.getUrl().endsWith("/test-path/test-image.jpg"));
    }

    @Test
    void saveImage_rollbackOnException() {
        // Given
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                "test-content".getBytes()
        );

        ImageCommand imageCommand = ImageCommand.builder()
                .id("image_0869")
                .file(mockFile)
                .kind(ImageKind.USER)
                .build();

        // Mock 파일 업로드 실패를 유도
        Mockito.when(fileStorageService.uploadImage(mockFile))
                .thenThrow(new ImageStorageException(ImageErrorCode.IMAGE_DELETE_ERROR));

        // When
        assertThrows(ImageStorageException.class, () -> imageService.saveImage(imageCommand));

        // Then
        // 데이터베이스에 ImageMetaData가 저장되지 않았는지 확인
        Optional<ImageMetaData> metaData = mongoDBImageMetaDataRepository.findById("image_0869");
        assertFalse(metaData.isPresent());
    }
}

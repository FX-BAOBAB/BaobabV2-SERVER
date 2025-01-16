import file.core.LocalFileAndDBImageStorageService;
import file.core.LocalFileStorageService;
import file.core.MongoDBImageMetaDataService;
import file.domain.ImageCommand;
import file.domain.ImageMetaData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import utils.FileTestUtils;

import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReadImageTest {
    @InjectMocks
    private LocalFileAndDBImageStorageService localFileAndDBImageStorageService;

    @Mock
    private LocalFileStorageService fileStorageService;

    @Mock
    private MongoDBImageMetaDataService imageMetaDataService;

    @Test
    void 이미지_조회_성공하면_url을_반환한다() throws ExecutionException, InterruptedException {
        // 이미지 저장 수행
        ImageMetaData imageMetaData = this.saveImage("fileName.jpg", "content");

        when(imageMetaDataService.findImageUrl(imageMetaData.getId())).thenReturn(imageMetaData.getUrl());
        Path mockPath = FileTestUtils.makeMockPath(imageMetaData.getServerName() + imageMetaData.getExtension());

        // 이미지 조회
        ReflectionTestUtils.setField(localFileAndDBImageStorageService, "uploadDir", "src/test/resources/images");
        String imageUrl = localFileAndDBImageStorageService.findImageUrl(imageMetaData.getId()).get();

        assertNotNull(imageUrl);
        assertThat(imageUrl).isEqualTo(imageMetaData.getUrl());
    }

    private ImageMetaData saveImage(String fileName, String content) throws ExecutionException, InterruptedException {
        MockMultipartFile mockFile = FileTestUtils.makeMockMultipartFile(fileName, content);
        ImageCommand mockImageCommand = FileTestUtils.makeMockImageCommand(mockFile);
        Path mockPath = FileTestUtils.makeMockPath(mockFile.getOriginalFilename());
        ImageMetaData mockMetaData = FileTestUtils.makeMockImageMetaData(mockImageCommand, mockFile);

        // Mock 파일 업로드 동작
        when(fileStorageService.uploadImage(any(MockMultipartFile.class))).thenReturn(mockPath);
        // Mock 메타데이터 저장 동작
        when(imageMetaDataService.saveImage(any(ImageCommand.class), any(Path.class))).thenReturn(mockMetaData);

        // 이미지 저장 수행
        ImageMetaData imageMetaData = localFileAndDBImageStorageService.saveImage(mockImageCommand).get();

        // Mock 객체의 동작 검증
        Mockito.verify(fileStorageService, Mockito.times(1)).uploadImage(mockFile);
        Mockito.verify(imageMetaDataService, Mockito.times(1)).saveImage(mockImageCommand, mockPath);

        return imageMetaData;
    }
}

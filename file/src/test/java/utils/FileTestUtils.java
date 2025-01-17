package utils;

import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTestUtils {

    public static MockMultipartFile makeMockMultipartFile(String filename, String content) {
        return new MockMultipartFile(
                "file",
                filename,
                "image/jpeg",
                content.getBytes()
        );
    }

    public static ImageCommand makeMockImageCommand(String imageId, MockMultipartFile mockFile) {
        return ImageCommand.builder()
                .id(imageId)
                .file(mockFile)
                .kind(ImageKind.USER)
                .build();
    }

    public static ImageMetaData makeMockImageMetaData(ImageCommand imageCommand, MockMultipartFile mockFile) {
        return ImageMetaData.builder()
                .id(imageCommand.getId())
                .url("http://localhost:8080/images/fileName.jpg")
                .serverName("testServerName")
                .originalName("fileName")
                .kind(imageCommand.getKind())
                .extension(".jpg")
                .build();
    }

    public static Path makeMockPath(String filename) {
        return Paths.get("src/test/resources/images" + filename);
    }
}

package file.adapter.output;

import file.application.port.output.MongoDBImageMetaDataRepository;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MongoDBImageMetaDataRepositoryTest {
    @Autowired
    private MongoDBImageMetaDataRepository mongoDBImageMetaDataRepository;

    @Test
    void save() {
        ImageMetaData metaData = ImageMetaData.builder()
                .url("test")
                .kind(ImageKind.valueOf("ARTICLE"))
                .originalName("test")
                .serverName("test")
                .build();

        ImageMetaData savedMetaData = mongoDBImageMetaDataRepository.save(metaData);

        assertThat(savedMetaData.getUrl()).isEqualTo(metaData.getUrl());
    }
}
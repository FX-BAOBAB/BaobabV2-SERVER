package file.adapter.input;

import file.application.port.input.ImageStorageService;
import file.application.port.input.ImageConsumer;
import file.application.port.output.ImageProducer;
import file.domain.ImageMetaData;
import file.domain.ImageRequest;
import file.domain.ImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaImageConsumer implements ImageConsumer {
    private final ImageStorageService imageStorageService;
    private final ImageProducer imageProducer;

    // TODO: topics 값 설졍
    @KafkaListener(topics = "temp")
    @Override
    public void consume(ImageRequest imageRequest) {
        log.info("consume image: {}", imageRequest);

        ImageMetaData imageMetaData = imageStorageService.saveImage(imageRequest);
        ImageResponse imageResponse = new ImageResponse(imageMetaData.getId(), imageMetaData.getUrl());
        // TODO:  topics 값 설졍
        imageProducer.send("SAVE_IMAGE", imageResponse);
    }
}

package file.application.port.output;

import file.domain.ImageResponse;

import java.util.List;

public interface ImageProducer {
    void send(String topic, ImageResponse response);
}

package file.application.port.input;

import file.domain.ImageRequest;

import java.util.List;

public interface ImageConsumer {
    void consume(ImageRequest imageRequest);
}
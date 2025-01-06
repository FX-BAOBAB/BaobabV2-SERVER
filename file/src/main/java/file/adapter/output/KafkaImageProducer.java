package file.adapter.output;

import file.application.port.output.ImageProducer;
import file.domain.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableKafka
@Component
@RequiredArgsConstructor
public class KafkaImageProducer implements ImageProducer {
    private final KafkaTemplate<String, ImageResponse> kafkaTemplate;

    @Override
    public void send(String topic, ImageResponse response) {
        kafkaTemplate.send(topic, response);
    }
}

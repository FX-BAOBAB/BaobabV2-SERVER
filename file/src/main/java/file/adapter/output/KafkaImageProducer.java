package file.adapter.output;

import file.domain.ImageResponse;
import global.message.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
@RequiredArgsConstructor
public class KafkaImageProducer implements MessageProducer<ImageResponse> {
    private final KafkaTemplate<String, ImageResponse> kafkaTemplate;

    @Override
    public void send(String topic, ImageResponse response) {
        kafkaTemplate.send(topic, response);
    }
}

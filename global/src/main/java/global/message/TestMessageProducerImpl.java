package global.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestMessageProducerImpl implements MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

}

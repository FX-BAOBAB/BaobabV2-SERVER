package global.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestMessageProducerImpl implements MessageProducer<UserMessage> {

    private final KafkaTemplate<String, UserMessage> kafkaTemplate;

    @Override
    public void send(String topic, UserMessage message) {
        kafkaTemplate.send(topic, message);
    }

}

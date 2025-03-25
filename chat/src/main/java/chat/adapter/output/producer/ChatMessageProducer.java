package chat.adapter.output.producer;

import global.message.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageProducer<T> implements MessageProducer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void send(String topic, T message) {
        log.info("Kafka send : {}", message);
        kafkaTemplate.send(topic, message);
    }

}

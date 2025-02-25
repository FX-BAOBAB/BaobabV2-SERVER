package chat.adapter.output.producer;

import global.message.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessageProducer<T> implements MessageProducer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void send(String topic, T message) {
        kafkaTemplate.send(topic, message);
    }

}

package chat.adapter.output.message;

import chat.application.port.output.KafkaProducerPort;
import chat.domain.ChatMessage;
import global.annotation.output.MessageOutputAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@MessageOutputAdapter
@RequiredArgsConstructor
public class KafkaProducerAdapter implements KafkaProducerPort {

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @Override
    public void send(String topic, ChatMessage message) {
        kafkaTemplate.send(topic, message);
    }

}

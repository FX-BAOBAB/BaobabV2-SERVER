package global.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestMessageConsumerImpl implements MessageConsumer {

    @KafkaListener(topics = "test-topic")
    @Override
    public String consumeMessage(String message) {
        log.info(message);
        return message;
    }
}

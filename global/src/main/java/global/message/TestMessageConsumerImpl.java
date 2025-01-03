package global.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestMessageConsumerImpl implements MessageConsumer<UserMessage> {

    @KafkaListener(topics = "test-topic")
    @Override
    public UserMessage consumeMessage(UserMessage message) {
        log.info(message.toString());
        return message;
    }
}

package chat.application;

import chat.application.port.input.MessageProducerUseCase;
import chat.application.port.output.KafkaProducerPort;
import chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducerService implements MessageProducerUseCase {

    private final KafkaProducerPort kafkaProducerPort;

    private static final String TOPIC_NAME = "chatMessage";

    @Override
    public void produceMessage(ChatMessage message) {
        kafkaProducerPort.send(TOPIC_NAME, message);
    }

}

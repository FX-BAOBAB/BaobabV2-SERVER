package chat.adapter.output.message;

import chat.application.port.output.KafkaProducerPort;
import chat.domain.ChatMessage;
import global.annotation.output.MessageOutputAdapter;
import global.message.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MessageOutputAdapter
@RequiredArgsConstructor
public class KafkaProducerAdapter implements KafkaProducerPort {

    private final MessageProducer<ChatMessage> messageProducer;

    @Override
    public void send(String topic, ChatMessage message) {
        messageProducer.send(topic, message);
    }

}

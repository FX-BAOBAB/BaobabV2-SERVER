package chat.application.port.output;

import chat.domain.ChatMessage;
import global.message.MessageProducer;
import global.message.UserMessage;

public interface KafkaProducerPort extends MessageProducer<ChatMessage> {

    @Override
    void send(String topic, ChatMessage message);

}

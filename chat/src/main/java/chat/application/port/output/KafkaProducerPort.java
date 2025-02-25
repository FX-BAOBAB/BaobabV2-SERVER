package chat.application.port.output;

import chat.domain.ChatMessage;

public interface KafkaProducerPort {

    void send(String topic, ChatMessage message);

}

package chat.application.port.input;

import chat.domain.ChatMessage;

public interface MessageProducerUseCase {

    void produceMessage(ChatMessage message);

}

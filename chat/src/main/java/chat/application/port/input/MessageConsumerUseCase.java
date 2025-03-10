package chat.application.port.input;

import chat.domain.ChatMessage;

public interface MessageConsumerUseCase {

    void consumeMessage(ChatMessage chatMessage);

}

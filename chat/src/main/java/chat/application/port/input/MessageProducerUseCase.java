package chat.application.port.input;

import chat.domain.command.ChatMessageCommand;

public interface MessageProducerUseCase {

    boolean produceMessage(ChatMessageCommand command);

}

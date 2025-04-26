package chat.application.port.input;

import chat.adapter.input.web.response.ChatMessageResponse;
import chat.domain.command.ChatMessageCommand;

public interface MessageProducerUseCase {

    ChatMessageResponse produceMessage(ChatMessageCommand command);

}

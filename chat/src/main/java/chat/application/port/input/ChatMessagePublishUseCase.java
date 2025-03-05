package chat.application.port.input;

import chat.domain.command.ChatMessagePublishCommand;

public interface ChatMessagePublishUseCase {

    boolean publishMessage(ChatMessagePublishCommand command);

}

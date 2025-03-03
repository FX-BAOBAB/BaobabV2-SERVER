package chat.application.port.input;

import chat.domain.command.ChatMessagePublishCommand;

public interface ChatMessageSaveUseCase {

    boolean saveChatMessage(ChatMessagePublishCommand command);

}

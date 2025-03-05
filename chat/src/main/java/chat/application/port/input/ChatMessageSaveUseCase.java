package chat.application.port.input;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.command.ChatMessagePublishCommand;

public interface ChatMessageSaveUseCase {

    MessageDocument saveChatMessage(ChatMessagePublishCommand command);

}

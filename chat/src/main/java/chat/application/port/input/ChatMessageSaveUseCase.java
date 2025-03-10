package chat.application.port.input;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.command.ChatMessageCommand;

public interface ChatMessageSaveUseCase {

    MessageDocument saveChatMessage(ChatMessageCommand command);

}

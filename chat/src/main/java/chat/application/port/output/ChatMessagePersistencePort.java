package chat.application.port.output;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.dto.ChatMessageSaveForm;

public interface ChatMessagePersistencePort {

    MessageDocument saveMessage(ChatMessageSaveForm chatMessageSaveForm);

}

package chat.application.port.output;

import chat.domain.dto.ChatMessageSaveForm;

public interface ChatMessagePersistencePort {

    boolean saveMessage(ChatMessageSaveForm chatMessageSaveForm);

}

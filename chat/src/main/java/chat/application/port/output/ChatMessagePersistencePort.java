package chat.application.port.output;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.dto.ChatMessageSaveForm;
import chat.domain.dto.ChatMessageSearchForm;
import java.util.List;

public interface ChatMessagePersistencePort {

    MessageDocument saveMessage(ChatMessageSaveForm chatMessageSaveForm);

    List<MessageDocument> getMessages(ChatMessageSearchForm chatMessageSearchForm);

}

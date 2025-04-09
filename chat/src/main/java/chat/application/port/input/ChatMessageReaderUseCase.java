package chat.application.port.input;

import chat.adapter.input.web.response.ChatMessageResponse;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.command.ChatMessageSearchCommand;
import java.util.List;

public interface ChatMessageReaderUseCase {

    List<ChatMessageResponse> getChatMessages(ChatMessageSearchCommand command);

}

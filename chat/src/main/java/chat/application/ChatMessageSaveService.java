package chat.application;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.ChatMessageSaveUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.domain.command.ChatMessageCommand;
import chat.domain.dto.ChatMessageSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageSaveService implements ChatMessageSaveUseCase {

    private final  ChatMessagePersistencePort chatMessagePersistencePort;

    @Override
    public MessageDocument saveChatMessage(ChatMessageCommand command) {
        return chatMessagePersistencePort.saveMessage(ChatMessageSaveForm.of(command));
    }

}

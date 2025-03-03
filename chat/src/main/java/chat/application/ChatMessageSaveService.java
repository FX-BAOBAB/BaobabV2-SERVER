package chat.application;

import chat.application.port.input.ChatMessageSaveUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.domain.command.ChatMessagePublishCommand;
import chat.domain.dto.ChatMessageSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageSaveService implements ChatMessageSaveUseCase {

    private final  ChatMessagePersistencePort chatMessagePersistencePort;

    @Override
    public boolean saveChatMessage(ChatMessagePublishCommand command) {
        return chatMessagePersistencePort.saveMessage(ChatMessageSaveForm.of(command));
    }

}

package chat.application;

import chat.application.port.input.ChatMessageSaveUseCase;
import chat.domain.command.ChatMessagePublishCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessagePublishService {

    private final ChatMessageSaveUseCase chatMessageSaveUseCase;

    public boolean publishMessage(ChatMessagePublishCommand command) {
        chatMessageSaveUseCase.saveChatMessage(command);

        return true;
    }

}

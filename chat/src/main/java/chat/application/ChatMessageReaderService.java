package chat.application;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.ChatMessageReaderUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.domain.command.ChatMessageSearchCommand;
import chat.domain.dto.ChatMessageSearchForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageReaderService implements ChatMessageReaderUseCase {

    private final ChatMessagePersistencePort chatMessagePersistencePort;
    private final ChatRoomCheckService chatRoomCheckService;

    @Override
    public List<MessageDocument> getChatMessages(ChatMessageSearchCommand command) {
        chatRoomCheckService.existsChatRoomBy(List.of(command.getChatRoomId()),
            command.getUserId()); // TODO 예외처리하자!

        return chatMessagePersistencePort.getMessages(ChatMessageSearchForm.of(command));
    }
}

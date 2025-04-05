package chat.application;

import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.input.ChatMessageReaderUseCase;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.core.common.error.ChatErrorCode;
import chat.core.common.exception.chatroom.ChatRoomNotFoundException;
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
        return chatRoomCheckService.existsChatRoomBy(List.of(command.getChatRoomId()),
                command.getUserId())
            .map(x -> chatMessagePersistencePort.getMessages(
                ChatMessageSearchForm.of(command)))
            .orElseThrow(() -> new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));
    }

}

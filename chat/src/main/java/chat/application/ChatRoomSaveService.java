package chat.application;

import chat.application.port.input.ChatRoomSaveUseCase;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.domain.command.ChatRoomSaveCommand;
import chat.domain.dto.ChatRoomSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomSaveService implements ChatRoomSaveUseCase {

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    @Override
    public Boolean saveChatRoom(ChatRoomSaveCommand chatRoomSaveCommand) {
        return chatRoomPersistencePort.saveChatRoom(ChatRoomSaveForm.of(chatRoomSaveCommand));
    }

}

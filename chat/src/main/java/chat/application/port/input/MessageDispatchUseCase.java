package chat.application.port.input;

import chat.domain.ChatMessage;

public interface MessageDispatchUseCase {

    void dispatchMessage(ChatMessage chatMessage);

}

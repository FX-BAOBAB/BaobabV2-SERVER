package chat.adapter.input.web;

import chat.adapter.input.web.request.ChatMessageRequest;
import chat.adapter.input.web.response.ChatRoomResponse;
import chat.application.port.input.ChatRoomReaderUseCase;
import chat.application.port.input.MessageDispatchUseCase;
import chat.application.port.input.MessageProducerUseCase;
import chat.domain.ChatMessage;
import chat.domain.command.ChatMessageCommand;
import chat.domain.command.ChatRoomReaderCommand;
import global.annotation.AuthenticatedUser;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestAdapter
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatRoomReaderUseCase chatRoomReaderUseCase;
    private final MessageProducerUseCase messageProducerUseCase;
    private final MessageDispatchUseCase messageDispatchUseCase;

    @GetMapping("/chat-room/{articleId}")
    public Api<ChatRoomResponse> enterChatRoom(
        @AuthenticatedUser AuthUser authUser,
        @PathVariable String articleId
    ) {
        String chatRoomId = chatRoomReaderUseCase.getChatRoom(
            ChatRoomReaderCommand.of(articleId, authUser.getUserId()));
        return Api.OK(ChatRoomResponse.of(chatRoomId));
    }

    @PostMapping("/message")
    public Api<Boolean> sendMessage(
        @AuthenticatedUser AuthUser authUser,
        @Valid @RequestBody Api<ChatMessageRequest> request
    ) {
        boolean isSent = messageProducerUseCase.produceMessage(
            ChatMessageCommand.of(request.getBody(), authUser.getUserId()));
        return Api.OK(isSent);
    }

    @PostMapping("/feign/message")
    public void sendMessage(ChatMessage chatMessage) {
        log.info("Feign 메시지 수신 : {}", chatMessage);
        messageDispatchUseCase.dispatchMessage(chatMessage);
    }

}

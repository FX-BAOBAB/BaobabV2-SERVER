package chat.adapter.input.web;

import chat.adapter.input.web.request.ChatMessageRequest;
import chat.adapter.input.web.response.ChatRoomResponse;
import chat.application.ChatConnectionService;
import chat.application.port.input.ChatRoomReaderUseCase;
import chat.application.port.input.MessageDispatchUseCase;
import chat.application.port.input.MessageProducerUseCase;
import chat.application.sse.SseConnectionStore;
import chat.application.sse.SseEmitterManager;
import chat.application.sse.SseMessageManager;
import chat.application.sse.UserSseConnection;
import chat.domain.ChatMessage;
import chat.domain.command.ChatMessageCommand;
import chat.domain.command.ChatRoomReaderCommand;
import global.annotation.AuthenticatedUser;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestAdapter
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatRoomReaderUseCase chatRoomReaderUseCase;
    private final MessageProducerUseCase messageProducerUseCase;
    private final MessageDispatchUseCase messageDispatchUseCase;
    private final ChatConnectionService chatConnectionService;

    private final SseConnectionStore<String, UserSseConnection> sseConnectionStore;

    @GetMapping(value = "/chat-room/{articleId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> enterChatRoom(
        @AuthenticatedUser AuthUser authUser,
        @PathVariable String articleId
    ) {
        ChatRoomResponse chatRoom = chatRoomReaderUseCase.getChatRoom(
            ChatRoomReaderCommand.of(articleId, authUser.getUserId()));
        return ResponseEntity
            .ok()
            .header("chatRoomId", chatRoom.getChatRoomId())
            .body(chatRoom.getConnection().getSseEmitter());
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
    public void sendMessage(@RequestBody ChatMessage chatMessage) {
        log.info("Feign 메시지 수신 : {}", chatMessage);
        messageDispatchUseCase.dispatchMessage(chatMessage);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping(value = "/connect/{userId}/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connectTest(@PathVariable String userId,
        @PathVariable String chatRoomId) {
        log.info("userId : {}", userId);
        log.info("chatRoomId : {}", chatRoomId);

        UserSseConnection userSseConnection = chatConnectionService.connectChatRoom(userId);
        System.out.println("연결성공");

//        try {
//            userSseConnection.getSseEmitter().send("Hello");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .header("chatRoomId", "test")
            .body(userSseConnection.getSseEmitter());

    }

    @GetMapping("/push-event/{userId}")
    public void pushEvent(@PathVariable String userId) {
        log.info("userID : {}", userId);
        List<UserSseConnection> emitter = sseConnectionStore.findEmitter(List.of(userId));
        UserSseConnection userSseConnection = emitter.get(0);

        SseEmitter sseEmitter = userSseConnection.getSseEmitter();

        try {
            sseEmitter.send("hello");
            log.info("전송 했음");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        sseMessageManager.sendMessage(emitter.get(0), "Test", "test message");
    }


}

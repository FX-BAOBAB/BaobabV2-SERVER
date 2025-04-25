package chat.adapter.input.web;

import chat.adapter.input.web.request.ChatMessageRequest;
import chat.adapter.input.web.request.ChatMessageSearchCondition;
import chat.adapter.input.web.response.ChatMessageResponse;
import chat.adapter.input.web.response.ChatRoomEnterResponse;
import chat.adapter.input.web.response.ChatRoomResponse;
import chat.application.port.input.ChatConnectionUseCase;
import chat.application.port.input.ChatMessageReaderUseCase;
import chat.application.port.input.ChatRoomEnterUseCase;
import chat.application.port.input.ChatRoomReaderUseCase;
import chat.application.port.input.MessageDispatchUseCase;
import chat.application.port.input.MessageProducerUseCase;
import chat.domain.ChatMessage;
import chat.domain.command.ChatMessageCommand;
import chat.domain.command.ChatMessageSearchCommand;
import chat.domain.command.ChatRoomReaderCommand;
import chat.domain.command.ChatRoomSearchCommand;
import global.annotation.AuthenticatedUser;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestAdapter
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatRoomEnterUseCase chatRoomEnterUseCase;
    private final MessageProducerUseCase messageProducerUseCase;
    private final MessageDispatchUseCase messageDispatchUseCase;
    private final ChatMessageReaderUseCase chatMessageReaderUseCase;
    private final ChatConnectionUseCase chatConnectionUseCase;
    private final ChatRoomReaderUseCase chatRoomReaderUseCase;

    private static final String CHAT_ROOM_ID_HEADER = "chatRoomIdHeader";

    @GetMapping(value = "/chat-room/{articleId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> enterChatRoom(
        @AuthenticatedUser AuthUser authUser,
        @PathVariable String articleId
    ) {
        ChatRoomEnterResponse chatRoom = chatRoomEnterUseCase.enterChatRoom(
            ChatRoomReaderCommand.of(articleId, authUser.getUserId()));
        return ResponseEntity
            .ok()
            .header(CHAT_ROOM_ID_HEADER, chatRoom.getChatRoomId())
            .body(chatRoom.getSseEmitter());
    }

    @PostMapping("/message")
    public Api<ChatMessageResponse> sendMessage(
        @AuthenticatedUser AuthUser authUser,
        @Valid @RequestBody Api<ChatMessageRequest> request
    ) {
        log.info("메시지 전송 요청 : {}", request.getBody());
        return Api.OK(messageProducerUseCase.produceMessage(
            ChatMessageCommand.of(request.getBody(), authUser.getUserId())));
    }

    @PostMapping("/feign/message")
    public void sendMessage(@RequestBody ChatMessage chatMessage) {
        log.info("Feign 메시지 수신 : {}", chatMessage);
        messageDispatchUseCase.dispatchMessage(chatMessage);
    }

    @GetMapping("/messages")
    public Api<List<ChatMessageResponse>> getChatMessages(
        @AuthenticatedUser AuthUser authUser,
        @ModelAttribute @Valid ChatMessageSearchCondition condition,
        @PageableDefault(sort = "sentAt", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {
        return Api.OK(chatMessageReaderUseCase.getChatMessages(
            ChatMessageSearchCommand.of(authUser.getUserId(), condition, pageable)));
    }

    @GetMapping("/exit/{chatRoomId}")
    public Api<Boolean> exitChatRoom(
        @AuthenticatedUser AuthUser authUser,
        @PathVariable String chatRoomId
    ) {
        chatConnectionUseCase.disconnectChatRoom(authUser.getUserId(), chatRoomId);
        return Api.OK(true);
    }

    @GetMapping("/rooms")
    public Api<List<ChatRoomResponse>> getChatRooms(
        @AuthenticatedUser AuthUser authUser,
        @RequestParam(required = false) LocalDateTime lastChatAt,
        @PageableDefault(sort = "lastChatAt", direction = Sort.Direction.DESC, size = 10) Pageable pageable
    ) {
        return Api.OK(chatRoomReaderUseCase.getChatRooms(
            ChatRoomSearchCommand.of(authUser.getUserId(), lastChatAt, pageable)));
    }

}

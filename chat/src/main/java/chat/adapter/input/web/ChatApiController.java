package chat.adapter.input.web;

import chat.adapter.input.web.response.ChatRoomResponse;
import chat.application.port.input.ChatRoomReaderUseCase;
import chat.domain.command.ChatRoomReaderCommand;
import global.annotation.AuthenticatedUser;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.resolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestAdapter
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatRoomReaderUseCase chatRoomReaderUseCase;

    @GetMapping("/chat-room/{articleId}")
    public Api<ChatRoomResponse> enterChatRoom(
        @AuthenticatedUser AuthUser authUser,
        @PathVariable String articleId
    ) {
        String chatRoomId = chatRoomReaderUseCase.getChatRoom(
            ChatRoomReaderCommand.of(articleId, authUser.getUserId()));
        return Api.OK(ChatRoomResponse.of(chatRoomId));
    }

}

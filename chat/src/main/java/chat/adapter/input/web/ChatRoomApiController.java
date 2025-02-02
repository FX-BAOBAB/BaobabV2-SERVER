package chat.adapter.input.web;

import chat.application.port.input.ChatRoomSaveUseCase;
import chat.core.common.annotation.CheckChatRoomExists;
import chat.domain.command.ChatRoomSaveCommand;
import global.annotation.AuthenticatedUser;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.resolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestAdapter
@RequiredArgsConstructor
public class ChatRoomApiController {

    private final ChatRoomSaveUseCase chatRoomSaveUseCase;

    @PostMapping("/{articleId}")
    @CheckChatRoomExists
    public Api<Boolean> saveChatRoom(
        @PathVariable String articleId, @AuthenticatedUser AuthUser authUser
    ) {
        Boolean isSaved = chatRoomSaveUseCase.saveChatRoom(
            ChatRoomSaveCommand.of(articleId, authUser.getUserId()));
        return Api.OK(isSaved);
    }

}

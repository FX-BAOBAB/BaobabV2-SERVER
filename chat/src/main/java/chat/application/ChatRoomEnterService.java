package chat.application;

import chat.adapter.input.web.response.ChatRoomEnterResponse;
import chat.adapter.output.client.ArticleClient;
import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.application.chatroom.ChatRoom;
import chat.application.chatroom.ChatRoomGenerator;
import chat.application.port.input.ChatRoomEnterUseCase;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.application.userchat.UserChat;
import chat.application.userchat.UserChatGenerator;
import chat.core.common.error.ChatErrorCode;
import chat.core.common.exception.chatroom.ChatRoomCreateFailedException;
import chat.core.common.exception.chatroom.ChatRoomNotFoundException;
import chat.domain.command.ChatRoomReaderCommand;
import chat.domain.dto.ChatRoomSaveForm;
import chat.domain.dto.UserChatSaveForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomEnterService implements ChatRoomEnterUseCase {

    private final ChatRoomCheckService chatRoomCheckService;
    private final UserChatSaveService userChatSaveService;
    private final ChatConnectionService chatConnectionService;

    private final ArticleClient articleClient;

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    private final ChatRoomGenerator chatRoomGenerator;
    private final UserChatGenerator userChatGenerator;

    @Transactional
    @Override
    public ChatRoomEnterResponse enterChatRoomByArticleId(ChatRoomReaderCommand command) {

        // 1. articleId 로 기준 채팅방 조회
        List<String> chatRoomIdList = chatRoomPersistencePort.getChatRoomListBy(
                command.getArticleId())
            .stream().map(ChatRoomDocument::getId).toList();

        // 2. 기존 채팅방이 존재하는지 확인
        String chatRoomId = chatRoomCheckService.existsChatRoomBy(chatRoomIdList,
                command.getBuyerId())
            .orElseGet(() -> createNewChatRoom(command));

        SseEmitter sseEmitter = chatConnectionService.connectChatRoom(
            command.getBuyerId(), chatRoomId);

        return ChatRoomEnterResponse.of(chatRoomId, sseEmitter);
    }

    @Transactional
    @Override
    public ChatRoomEnterResponse enterChatRoomByChatRoomId(String userId, String chatRoomId) {
        String existsChatRoomId = chatRoomCheckService.existsChatRoomBy(chatRoomId, userId)
            .orElseThrow(() -> new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));

        SseEmitter sseEmitter = chatConnectionService.connectChatRoom(
            userId, chatRoomId);

        return ChatRoomEnterResponse.of(existsChatRoomId, sseEmitter);
    }

    private String createNewChatRoom(ChatRoomReaderCommand command) {

        if (command.getBuyerId()
            .equals(articleClient.getArticleSimpleInfo(command.getArticleId()).getUserId())) {
            throw new ChatRoomCreateFailedException(ChatErrorCode.CHAT_ROOM_CREATION_FAILED);
        }

        // 3. ChatRoom 생성
        ChatRoom chatRoom = chatRoomGenerator.createChatRoom(command);
        String chatRoomId = chatRoomPersistencePort.saveChatRoom(ChatRoomSaveForm.of(chatRoom));

        // 4. UserChat 생성
        List<UserChat> userChatList = userChatGenerator.createUserChat(chatRoomId, command);
        userChatSaveService.saveUserChatIfNotExists(UserChatSaveForm.of(userChatList));

        return chatRoomId;
    }

}

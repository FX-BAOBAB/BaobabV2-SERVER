package chat.application;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.application.chatroom.ChatRoom;
import chat.application.chatroom.ChatRoomGenerator;
import chat.application.port.input.ChatRoomCheckUseCase;
import chat.application.port.input.UserChatSaveUseCase;
import chat.application.port.output.ChatRoomPersistencePort;
import chat.application.userchat.UserChat;
import chat.application.userchat.UserChatGenerator;
import chat.domain.command.ChatRoomSaveCommand;
import chat.domain.dto.ChatRoomSaveForm;
import chat.domain.dto.UserChatSaveForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomReaderService {

    private final ChatRoomCheckUseCase chatRoomCheckUseCase;

    private final UserChatSaveUseCase userChatSaveUseCase;

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    private final ChatRoomGenerator chatRoomGenerator;

    private final UserChatGenerator userChatGenerator;

    @Transactional
    public String getChatRoom(ChatRoomSaveCommand command) {

        // 1. articleId 로 기준 채팅방 조회
        List<String> chatRoomIdList = chatRoomPersistencePort.getChatRoomListBy(command.getArticleId())
            .stream().map(ChatRoomDocument::getId).toList();

        // 2. 기존 채팅방이 존재하는지 확인
        return chatRoomCheckUseCase.existsChatRoomBy(chatRoomIdList, command.getBuyerId())
            .orElseGet(() -> createNewChatRoom(command));
    }

    private String createNewChatRoom(ChatRoomSaveCommand command) {

        // 3. ChatRoom 생성
        ChatRoom chatRoom = chatRoomGenerator.createChatRoom(command);
        String chatRoomId = chatRoomPersistencePort.saveChatRoom(ChatRoomSaveForm.of(chatRoom));

        // 4. UserChat 생성
        List<UserChat> userChatList = userChatGenerator.createUserChat(chatRoomId, command);
        userChatSaveUseCase.saveUserChatIfNotExists(UserChatSaveForm.of(userChatList));

        return chatRoomId;
    }


}



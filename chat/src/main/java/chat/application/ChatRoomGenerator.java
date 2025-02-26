package chat.application;

import chat.adapter.output.persistence.repository.document.ChatRoomDocument;
import chat.application.factory.ChatRoomFactory;
import chat.application.factory.ChatRoomTitleStrategyFactory;
import chat.application.stragety.ChatRoomTitleStrategy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Component
@RequiredArgsConstructor
public class ChatRoomGenerator implements ChatRoomFactory {

    private final ChatRoomTitleStrategyFactory chatRoomTitleStrategyFactory;

    public String generateDefaultTitle(List<String> userNickNameList) {
        ChatRoomTitleStrategy titleGenerator = chatRoomTitleStrategyFactory.getTitleStrategy(userNickNameList);
        // TODO User Nick Name List 통신 필요 , Module 간 통신 FeignClient 이용
        return titleGenerator.generateTitle(userNickNameList);
    }

    @Override
    public ChatRoomDocument createChatRoom(List<String> userNickNameList) {
        String roomTitle = generateDefaultTitle(userNickNameList);
        return ChatRoomDocument.builder()
                .title(roomTitle)
                .build();
    }

}

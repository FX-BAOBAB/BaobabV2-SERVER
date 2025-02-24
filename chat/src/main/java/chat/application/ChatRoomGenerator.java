package chat.application;

import chat.application.factory.ChatRoomTitleStrategyFactory;
import chat.application.stragety.ChatRoomTitleStrategy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Component
@RequiredArgsConstructor
public class ChatRoomGenerator {

    private ChatRoomTitleStrategy strategy;

    private final ChatRoomTitleStrategyFactory chatRoomTitleStrategyFactory;

    public String generateDefaultTitle(List<String> nickNameList) {
        this.strategy = chatRoomTitleStrategyFactory.getTitleStrategy(nickNameList.size());
        return strategy.generateTitle(nickNameList);
    }

}

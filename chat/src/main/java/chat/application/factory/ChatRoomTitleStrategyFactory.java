package chat.application.factory;

import chat.application.stragety.ChatRoomTitleStrategy;
import chat.application.stragety.NormalTitleGenerator;
import chat.application.stragety.TruncatedTitleGenerator;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomTitleStrategyFactory {

    private static final int MAX_USER_TITLE = 5;

    public ChatRoomTitleStrategy getTitleStrategy(int size) {
        if (size > MAX_USER_TITLE) {
            return new TruncatedTitleGenerator();
        }
        return new NormalTitleGenerator();
    }

}

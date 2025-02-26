package chat.application.factory;

import chat.application.stragety.ChatRoomTitleStrategy;
import chat.application.stragety.NormalTitleGenerator;
import chat.application.stragety.TruncatedTitleGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatRoomTitleStrategyFactory {

    private static final int OrganizationCount = 2;

    public ChatRoomTitleStrategy getTitleStrategy(List<String> userNickNameList) {
        if (isOrganization(userNickNameList)) {
            return new TruncatedTitleGenerator();
        }
        return new NormalTitleGenerator();
    }


    private boolean isOrganization(List<String> userNickNameList){
        return userNickNameList.size() > OrganizationCount;
    }

}

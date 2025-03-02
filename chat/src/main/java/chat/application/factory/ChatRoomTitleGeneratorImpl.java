package chat.application.factory;

import chat.application.stragety.ChatRoomTitleStrategy;
import chat.application.stragety.NormalTitleGenerator;
import chat.application.stragety.TruncatedTitleGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomTitleGeneratorImpl implements ChatRoomTitleGenerator{

    private static final int OrganizationCount = 2;

    @Override
    public String getChatRoomTitle(List<String> userNickNameList) {
        return getTitleStrategy(userNickNameList.size()).generateTitle(userNickNameList);
    }

    public ChatRoomTitleStrategy getTitleStrategy(int nickNameListSize) {
        if (isOrganization(nickNameListSize)) {
            return TruncatedTitleGenerator.getGenerator();
        }
        return NormalTitleGenerator.getGenerator();
    }

    private boolean isOrganization(int NickNameListSize) {
        return NickNameListSize > OrganizationCount;
    }

}

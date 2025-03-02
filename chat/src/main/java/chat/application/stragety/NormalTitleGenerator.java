package chat.application.stragety;

import java.util.List;

public class NormalTitleGenerator implements ChatRoomTitleStrategy {

    private static final NormalTitleGenerator GENERATOR = new NormalTitleGenerator();

    private NormalTitleGenerator() {}

    public static NormalTitleGenerator getGenerator() {
        return GENERATOR;
    }

    @Override
    public String generateTitle(List<String> uesrNickNameList) {
        return String.join(", ", uesrNickNameList) + "의 채팅방";
    }

}

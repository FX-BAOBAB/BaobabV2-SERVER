package chat.application.stragety;

import java.util.List;

public class TruncatedTitleGenerator implements ChatRoomTitleStrategy {

    private static final TruncatedTitleGenerator GENERATOR = new TruncatedTitleGenerator();

    private TruncatedTitleGenerator() {}

    public static TruncatedTitleGenerator getGenerator() {
        return GENERATOR;
    }

    @Override
    public String generateTitle(List<String> userNickNameList) {
        return userNickNameList.get(0) + "님의 단톡방";
    }

}

package chat.application;

public class ChatRoomTitleGenerator {

    public String generateTitle(String sellerNickName, String buyerNickName) {
        return sellerNickName + buyerNickName + "의 채팅방";
    }

}

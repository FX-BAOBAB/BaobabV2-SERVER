package chat.application.sse;

public class ChatRedisKeyGenerator {

    private static final String PREFIX = "chat:";

    public static String getUniqueKey(String userId, String chatRoomId) {
        return PREFIX + userId + ":" + chatRoomId;
    }

}

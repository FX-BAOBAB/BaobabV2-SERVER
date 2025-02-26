package chat.application.port.input;

public interface UserChatSaveUseCase {

    void saveUserChatIfNotExists(String chatRoomId, String userId);

}

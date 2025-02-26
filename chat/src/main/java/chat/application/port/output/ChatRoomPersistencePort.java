package chat.application.port.output;

public interface ChatRoomPersistencePort {

    boolean existsChatRoomBy(String chatRoomId);

}

package global.message;

public interface MessageProducer {
    void send(String topic, String message);
}

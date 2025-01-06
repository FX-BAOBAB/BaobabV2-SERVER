package global.message;

public interface MessageProducer<T> {
    void send(String topic, T message);
}

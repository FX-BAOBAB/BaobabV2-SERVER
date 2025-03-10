package global.message;

public interface MessageConsumer<T> {
    void consumeMessage(T message);
}
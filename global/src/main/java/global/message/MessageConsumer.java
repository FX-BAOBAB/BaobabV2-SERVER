package global.message;

import org.apache.logging.log4j.message.Message;

public interface MessageConsumer<T> {
    T consumeMessage(T message);
}
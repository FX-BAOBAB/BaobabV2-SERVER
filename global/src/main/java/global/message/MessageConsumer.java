package global.message;

import org.apache.logging.log4j.message.Message;

public interface MessageConsumer {
    String consumeMessage(String message);
}
package message.application.port.input;

import message.domain.command.MessageCommand;

/**
 * Send Message Input Port
 */
public interface SendMessageUseCase {

    /**
     * Sends messages using the provided command.
     * @param command the message command containing the user IDs, title, and body
     */
    void send(MessageCommand command);

}

package message.application.port.input;

import com.google.firebase.messaging.FirebaseMessagingException;
import message.domain.command.MessageCommand;
import message.domain.command.MulticastMessageCommand;

/**
 * Send Message Input Port
 */
public interface SendMessageUseCase {

    /**
     * Sends a message to a single user using the provided command.
     * @param command the message command containing the user ID, title, and body
     * @throws FirebaseMessagingException if an error occurs while sending the message
     */
    void send(MessageCommand command) throws FirebaseMessagingException;

    /**
     * Sends a multicast message to multiple users using the provided command.
     * @param command the multicast message command containing user IDs, title, and body
     * @throws FirebaseMessagingException if an error occurs while sending one or more messages
     */
    void send(MulticastMessageCommand command) throws FirebaseMessagingException;

}

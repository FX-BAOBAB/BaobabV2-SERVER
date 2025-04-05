package message.application.port.input;

import com.google.firebase.messaging.FirebaseMessagingException;
import message.domain.command.MessageCommand;
import message.domain.command.MulticastMessageCommand;

public interface SendMessageUseCase {

    void send(MessageCommand command) throws FirebaseMessagingException;

    void send(MulticastMessageCommand command) throws FirebaseMessagingException;

}

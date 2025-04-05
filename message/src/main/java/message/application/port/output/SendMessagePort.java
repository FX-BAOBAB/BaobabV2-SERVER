package message.application.port.output;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.util.concurrent.ExecutionException;

public interface SendMessagePort {

   void send(Message message)
       throws FirebaseMessagingException, ExecutionException, InterruptedException;

}

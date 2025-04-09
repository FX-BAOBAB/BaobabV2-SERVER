package message.application.port.output;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.util.concurrent.ExecutionException;

/**
 * Send Message Port
 */
public interface SendMessagePort {

   /**
    * Sends a message using Firebase Cloud Messaging (FCM)
    * @param message Message
    * @throws FirebaseMessagingException Firebase Messaging Exception
    * @throws ExecutionException Execution Exception
    * @throws InterruptedException Interrupted Exception
    */
   void send(Message message)
       throws FirebaseMessagingException, ExecutionException, InterruptedException;

}

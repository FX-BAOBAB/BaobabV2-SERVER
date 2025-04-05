package token.application.port.output;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.util.List;

public interface SubscriptionPort {

    boolean subscribeToTopic(List<String> tokens, String topic) throws FirebaseMessagingException;

}

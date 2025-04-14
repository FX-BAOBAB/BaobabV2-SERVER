package token.application.port.output;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.util.List;

/**
 * Subscription Port
 */
public interface SubscriptionPort {

    /**
     * Subscribe to Topic
     *
     * @param tokens List of FCM Tokens
     * @param topic Topic Name
     * @return true if subscription is successful, false otherwise
     * @throws FirebaseMessagingException if an error occurs while subscribing to the topic
     */
    boolean subscribeToTopic(List<String> tokens, String topic) throws FirebaseMessagingException;

}

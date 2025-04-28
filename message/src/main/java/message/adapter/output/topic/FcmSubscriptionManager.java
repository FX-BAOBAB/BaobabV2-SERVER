package message.adapter.output.topic;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.application.port.output.SubscriptionPort;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmSubscriptionManager implements SubscriptionPort {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public boolean subscribeToTopic(List<String> tokens, String topic)
        throws FirebaseMessagingException {
        return firebaseMessaging.subscribeToTopic(tokens, topic).getSuccessCount() > 0;
    }
}

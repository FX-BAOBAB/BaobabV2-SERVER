package message.adapter.output.topic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FcmSubscriptionTest {

    @Value("${fcm.test.token}")
    private String testFcmToken;

    @Test
    void Topic_구독() throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
            List.of(testFcmToken), "all");
        assertNotNull(response);
    }

}
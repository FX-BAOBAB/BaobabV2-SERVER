package message.adapter.output.message;

import static org.junit.jupiter.api.Assertions.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FirebaseSendTest {

    @Value("${fcm.test.token}")
    private String testFcmToken;

    @Test
    void 메시지_전송() throws FirebaseMessagingException {
        Notification notification = Notification.builder()
            .setTitle("Test Title")
            .setBody("This is a test message")
            .build();

        Message message = Message.builder()
            .setToken(testFcmToken)
            .setNotification(notification)
            .build();

        String response = FirebaseMessaging.getInstance().send(message, true);
        assertNotNull(response);
    }
}

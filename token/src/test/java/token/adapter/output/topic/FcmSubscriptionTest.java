package token.adapter.output.topic;

import static org.junit.jupiter.api.Assertions.*;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FcmSubscriptionTest {

    @Value("${fcm.config.path}")
    private String fcmConfigFile;

    @Value("${fcm.test.token}")
    private String testFcmToken;

    @BeforeEach
    void setupFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) { // 중복 초기화 방지
            FileInputStream serviceAccount = new FileInputStream(fcmConfigFile);

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            FirebaseApp.initializeApp(options);
        }
    }

    @Test
    void Topic_구독() throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
            List.of(testFcmToken), "all");
        assertNotNull(response);
    }

}
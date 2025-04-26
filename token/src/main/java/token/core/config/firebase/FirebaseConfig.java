package token.core.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import token.core.common.error.FcmTokenErrorCode;
import token.core.common.exception.firebase.FirebaseInitializationFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Value("${fcm.config.path}")
    private String FCM_CONFIG_FILE;

    /**
     * FirebaseApp 초기화
     */
    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream(FCM_CONFIG_FILE);

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new FirebaseInitializationFailedException(
                FcmTokenErrorCode.FIREBASE_INITIALIZATION_FAILED);
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}

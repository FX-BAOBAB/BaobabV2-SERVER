package message.core.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import message.core.common.error.MessageErrorCode;
import message.core.common.exception.firebase.FirebaseInitializationFailedException;
import message.core.config.async.CustomThreadManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${fcm.config.path}")
    private String fcmConfigFilePath;

    /**
     * FirebaseApp 초기화
     */
    @PostConstruct
    public void initializeFirebaseApp() {
        if (FirebaseApp.getApps().isEmpty()) {
            initializeFirebase();
        }
    }

    private void initializeFirebase() {
        InputStream serviceAccount = getServiceAccountStream();
        try {
            FirebaseApp.initializeApp(buildFirebaseOptions(serviceAccount));
            log.info("FirebaseApp initialized successfully.");
        } catch (IOException e) {
            throw new FirebaseInitializationFailedException(
                MessageErrorCode.FIREBASE_INITIALIZATION_FAILED);
        }
    }

    private InputStream getServiceAccountStream() {
        InputStream serviceAccount = getClass().getClassLoader()
            .getResourceAsStream(fcmConfigFilePath);

        if (serviceAccount == null) {
            log.error("Service account file not found: {}", fcmConfigFilePath);
            throw new FirebaseInitializationFailedException(
                MessageErrorCode.FIREBASE_INITIALIZATION_FAILED);
        }

        return serviceAccount;
    }

    private FirebaseOptions buildFirebaseOptions(InputStream serviceAccount) throws IOException {
        return FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setThreadManager(new CustomThreadManager())
            .build();
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}
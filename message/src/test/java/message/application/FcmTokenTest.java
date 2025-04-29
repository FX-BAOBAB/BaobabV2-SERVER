package message.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import message.adapter.output.persistence.FcmTokenPersistenceAdapter;
import message.config.EnableMongoTestServer;
import message.domain.command.FcmTokenSaveCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableMongoTestServer
@ActiveProfiles("test")
class FcmTokenTest {

    @Autowired
    private FcmTokenService fcmTokenService;

    @Autowired
    private FcmTokenPersistenceAdapter fcmTokenPersistenceAdapter;

    @Test
    void 토큰_저장() {
        FcmTokenSaveCommand command = FcmTokenSaveCommand.builder()
            .token("test-token")
            .userId("test-user-id")
            .build();

        assertTrue(fcmTokenService.saveFcmToken(command));
    }

    @Test
    void 토큰_삭제() {
        fcmTokenService.deleteFcmToken("test-token");
        List<String> tokens = fcmTokenPersistenceAdapter.findByUserIds(List.of("test-user-id"));
        assertTrue(tokens.isEmpty());
    }
}
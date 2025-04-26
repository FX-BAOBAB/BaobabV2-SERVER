package token.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import token.config.EnableMongoTestServer;
import token.domain.command.FcmTokenSaveCommand;

@SpringBootTest
@EnableMongoTestServer
@ActiveProfiles("test")
class FcmTokenTest {

    @Autowired
    private FcmTokenService fcmTokenService;

    @Test
    void 토큰_저장() {
        FcmTokenSaveCommand command = FcmTokenSaveCommand.builder()
            .token("test-token")
            .userId("test-user-id")
            .build();

        assertTrue(fcmTokenService.saveFcmToken(command));
    }

    @Test
    void 토큰_조회() {
        List<String> tokens = fcmTokenService.getFcmTokens(List.of("test-user-id"));
        assertEquals(tokens.getFirst(), "test-token");
    }

    @Test
    void 토큰_삭제() {
        fcmTokenService.deleteFcmToken("test-token");
        List<String> tokens = fcmTokenService.getFcmTokens(List.of("test-user-id"));
        assertTrue(tokens.isEmpty());
    }

}
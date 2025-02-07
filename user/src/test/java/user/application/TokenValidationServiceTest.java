package user.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import config.AcceptanceTestWithMongo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.domain.command.TokenCommand;
import user.utils.UserLoginUtils;
import user.utils.UserRegisterUtils;

@SpringBootTest
class TokenValidationServiceTest extends AcceptanceTestWithMongo {

    @Autowired
    private TokenValidationService tokenValidationService;

    @Autowired
    private UserRegisterUtils userRegisterUtils;

    @Autowired
    private UserLoginUtils userLoginUtils;

    @Test
    void 토큰_검증_성공() {

        // Given
        userRegisterUtils.registerUser("test@example.com", "Password12@");
        TokenCommand tokenCommand = userLoginUtils.loginUser("test@example.com", "Password12@");

        // When
        String bearerToken = "Bearer " + tokenCommand.getAccessToken();
        String userId = tokenValidationService.validateToken(bearerToken);

        // Then
        assertNotNull(userId);
    }
}

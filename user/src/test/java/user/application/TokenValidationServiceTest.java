package user.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.domain.command.TokenCommand;
import user.security.jwt.model.JwtInfoDto;
import user.utils.UserLoginUtils;
import user.utils.UserRegisterUtils;

@SpringBootTest
class TokenValidationServiceTest {

    @Autowired
    private TokenValidationService tokenValidationService;

    @Autowired
    private UserRegisterUtils userRegisterUtils;

    @Autowired
    private UserLoginUtils userLoginUtils;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @AfterEach
    void tearDown() {
        userMongoRepository.deleteByAccount_Email("test@example.com");
    }

    @Test
    void 토큰_검증_성공() {

        // Given
        userRegisterUtils.registerUser("test@example.com", "Password12@");
        TokenCommand tokenCommand = userLoginUtils.loginUser("test@example.com", "Password12@");

        // When
        String bearerToken = "Bearer " + tokenCommand.getAccessToken();
        JwtInfoDto jwtInfoDto = tokenValidationService.validateToken(bearerToken);

        // Then
        assertNotNull(jwtInfoDto);
    }
}
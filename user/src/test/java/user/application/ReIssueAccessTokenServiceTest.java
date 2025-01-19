package user.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.core.common.exception.token.TokenException;
import user.domain.command.TokenCommand;
import user.security.jwt.model.TokenDto;
import user.utils.UserLoginUtils;
import user.utils.UserRegisterUtils;

@SpringBootTest
class ReIssueAccessTokenServiceTest {

    @Autowired
    private ReIssueAccessTokenService reIssueAccessTokenService;

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
    void AccessToken_재발급_성공() {

        // Given
        userRegisterUtils.registerUser("test@example.com", "Password12@");
        TokenCommand tokenCommand = userLoginUtils.loginUser("test@example.com", "Password12@");

        // When
        String bearerToken = "Bearer " + tokenCommand.getRefreshToken();
        TokenDto tokenDto = reIssueAccessTokenService.reIssueAccessToken(bearerToken);

        // Then
        assertNotNull(tokenDto.getToken());
        assertThat(tokenDto.getExpiredAt()).isAfter(tokenCommand.getAccessTokenExpiredAt());
    }

    @Test
    void AccessToken_재발급_실패_위조된_토큰() {

        // Given
        userRegisterUtils.registerUser("test@example.com", "Password12@");
        TokenCommand tokenCommand = userLoginUtils.loginUser("test@example.com", "Password12@");

        // When
        String bearerToken = "Bearer abc" + tokenCommand.getRefreshToken();
        assertThrows(TokenException.class, () ->
            reIssueAccessTokenService.reIssueAccessToken(bearerToken)
        );
    }

}

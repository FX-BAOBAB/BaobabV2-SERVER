package user.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.core.common.exception.token.UserNotFoundException;
import user.core.common.exception.user.PasswordMismatchException;
import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;
import user.utils.UserRegisterUtils;

@SpringBootTest
class UserLoginServiceTest {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserRegisterUtils userRegisterUtils;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @AfterEach
    void tearDown() {
        userMongoRepository.deleteByAccount_Email("test@example.com");
    }

    @Test
    void 로그인_성공() {

        // Given
        userRegisterUtils.registerUser("test@example.com", "Password12@");
        UserLoginCommand userLoginCommand = UserLoginCommand.builder()
            .email("test@example.com")
            .password("Password12@")
            .build();

        // When
        TokenCommand tokenCommand = userLoginService.login(userLoginCommand);

        // Then
        assertThat(tokenCommand).isNotNull();
        assertThat(tokenCommand.getAccessToken()).isNotEmpty();
        assertThat(tokenCommand.getRefreshToken()).isNotEmpty();
        assertThat(tokenCommand.getAccessTokenExpiredAt()).isAfter(LocalDateTime.now());
        assertThat(tokenCommand.getRefreshTokenExpiredAt()).isAfter(tokenCommand.getAccessTokenExpiredAt());
    }

    @Test
    void 로그인_실패_잘못된_비밀번호() {

        // Given
        userRegisterUtils.registerUser("test@example.com", "Password12@");
        UserLoginCommand userLoginCommand = UserLoginCommand.builder()
            .email("test@example.com")
            .password("Password123@")
            .build();

        // When
        assertThrows(PasswordMismatchException.class, () -> {
            userLoginService.login(userLoginCommand);
        });
    }

    @Test
    void 로그인_실패_잘못된_이메일() {

        // Given
        userRegisterUtils.registerUser("test@example.com", "Password12@");
        UserLoginCommand userLoginCommand = UserLoginCommand.builder()
            .email("test1@example.com")
            .password("Password12@")
            .build();

        // When
        assertThrows(UserNotFoundException.class, () ->
            userLoginService.login(userLoginCommand)
        );
    }

}
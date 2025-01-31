package user.application;

import static org.assertj.core.api.Assertions.assertThat;

import config.AcceptanceTestWithMongo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.command.UserRegisterCommand;

@SpringBootTest
class UserRegisterServiceTest extends AcceptanceTestWithMongo {

    @Autowired
    private UserRegisterService userRegisterService;

    @Test
    void 회원가입_성공() {

        // Given
        UserRegisterCommand userRegisterCommand = getRegisterCommand();

        // When
        String userId = userRegisterService.register(userRegisterCommand);

        // Then
        assertThat(userId).isNotNull();

    }

    private UserRegisterCommand getRegisterCommand() {
        return UserRegisterCommand.builder()
            .email("test@example.com")
            .password("Password@123")
            .nickName("testUser")
            .name("Test User")
            .phone("010-0000-0000")
            .birth(LocalDate.of(2001, 9, 7))
            .address("Seoul")
            .detailAddress("Gangnam")
            .basicAddress("Seoul, Gangnam")
            .post("12345")
            .role(UserRole.BASIC_USER)
            .status(UserStatus.REGISTERED)
            .registeredAt(LocalDateTime.now())
            .build();
    }

}
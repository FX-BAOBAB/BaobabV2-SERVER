package user.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.domain.command.UserRegisterCommand;

@SpringBootTest
class UserRegisterServiceTest {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @AfterEach
    void tearDown() {
        userMongoRepository.deleteByAccount_Email("test@example.com");
    }

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
            .department("Software")
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
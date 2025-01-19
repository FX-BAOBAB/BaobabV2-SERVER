package user.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.domain.command.UserReaderCommand;
import user.utils.UserRegisterUtils;

@SpringBootTest
class UserReaderServiceTest {

    @Autowired
    private UserReaderService userReaderService;

    @Autowired
    private UserRegisterUtils userRegisterUtils;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @AfterEach
    void tearDown() {
        userMongoRepository.deleteByAccount_Email("test@example.com");
    }

    @Test
    void 사용자_조회_성공() {

        // Given
        String userId = userRegisterUtils.registerUser("test@example.com", "Password12@");

        // When
        UserReaderCommand userInfo = userReaderService.getUserInfoBy(userId);

        // Then
        assertNotNull(userInfo);
        assertEquals(userId, userInfo.getUserId());
        assertEquals("test@example.com", userInfo.getEmail());
        assertEquals("testUser", userInfo.getNickName());
        assertEquals("Test User", userInfo.getName());
        assertEquals("010-0000-0000", userInfo.getPhone());
        assertEquals("Software", userInfo.getDepartment());
        assertEquals(LocalDate.of(2001, 9, 7), userInfo.getBirth());
        assertEquals("Seoul", userInfo.getAddress());
        assertEquals("Gangnam", userInfo.getDetailAddress());
        assertEquals("Seoul, Gangnam", userInfo.getBasicAddress());
        assertEquals("12345", userInfo.getPost());
        assertEquals(UserRole.BASIC_USER, userInfo.getRole());
        assertEquals(UserStatus.REGISTERED, userInfo.getStatus());
        assertThat(userInfo.getRegisteredAt()).isBefore(LocalDateTime.now());

    }

}

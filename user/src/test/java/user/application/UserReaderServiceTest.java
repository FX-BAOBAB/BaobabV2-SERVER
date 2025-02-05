package user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import config.AcceptanceTestWithMongo;
import config.EnableMongoTestServer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.command.UserReaderCommand;
import user.utils.UserRegisterUtils;

@SpringBootTest
@EnableMongoTestServer
class UserReaderServiceTest extends AcceptanceTestWithMongo {

    @Autowired
    private UserReaderService userReaderService;

    @Autowired
    private UserRegisterUtils userRegisterUtils;

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
        assertEquals(CarrierType.KT, userInfo.getUserPhoneInfo().getCarrierType());
        assertEquals("010-0000-0000", userInfo.getUserPhoneInfo().getPhoneNumber());
        assertEquals(GenderType.MALE, userInfo.getGenderType());
        assertEquals(false, userInfo.getIsForeigner());
        assertEquals(LocalDate.of(2001, 9, 7), userInfo.getBirth());
        assertEquals("Seoul", userInfo.getUserAddress().getAddress());
        assertEquals("Gangnam", userInfo.getUserAddress().getDetailAddress());
        assertEquals("Seoul, Gangnam", userInfo.getUserAddress().getBasicAddress());
        assertEquals("12345", userInfo.getUserAddress().getPost());
        assertEquals(UserRole.BASIC_USER, userInfo.getRole());
        assertEquals(UserStatus.REGISTERED, userInfo.getStatus());
        assertThat(userInfo.getRegisteredAt()).isBefore(LocalDateTime.now());

    }

}

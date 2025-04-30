//package user.application;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import config.AcceptanceTestWithMongo;
//import config.EnableMongoTestServer;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import user.adapter.output.persistence.enums.CarrierType;
//import user.adapter.output.persistence.enums.GenderType;
//import global.user.UserRole;
//import user.adapter.output.persistence.enums.UserStatus;
//import user.adapter.output.persistence.repository.UserDocument;
//import user.application.port.output.UserPersistencePort;
//import user.domain.command.TokenCommand;
//import user.domain.command.UserReaderCommand;
//import user.utils.UserLoginUtils;
//import user.utils.UserRegisterUtils;
//
//@SpringBootTest
//@EnableMongoTestServer
//class UserReaderServiceTest extends AcceptanceTestWithMongo {
//
//    @Autowired
//    private UserReaderService userReaderService;
//
//    @Autowired
//    private UserPersistencePort userPersistencePort;
//
//    @Autowired
//    private UserRegisterUtils userRegisterUtils;
//
//    @Test
//    void 사용자_조회_성공() {
//
//        // Given
//        userRegisterUtils.registerUser("test@example.com", "Password12@");
//        UserDocument userDocument = userPersistencePort.getUserDocumentBy("test@example.com",
//            UserStatus.REGISTERED);
//
//        // When
//        UserReaderCommand userInfo = userReaderService.getUserInfoBy(userDocument.getId());
//
//        // Then
//        assertNotNull(userInfo);
//        assertEquals(userDocument.getId(), userInfo.getUserId());
//        assertEquals("test@example.com", userInfo.getEmail());
//        assertEquals("testUser", userInfo.getNickName());
//        assertEquals("Test User", userInfo.getName());
//        assertEquals(CarrierType.KT, userInfo.getUserPhoneInfo().getCarrierType());
//        assertEquals("010-0000-0000", userInfo.getUserPhoneInfo().getPhoneNumber());
//        assertEquals(GenderType.MALE, userInfo.getGenderType());
//        assertEquals(false, userInfo.getIsForeigner());
//        assertEquals(LocalDate.of(2001, 9, 7), userInfo.getBirth());
//        assertEquals("Seoul", userInfo.getUserAddress().getAddress());
//        assertEquals("Gangnam", userInfo.getUserAddress().getDetailAddress());
//        assertEquals(true, userInfo.getUserAddress().getBasicAddress());
//        assertEquals("12345", userInfo.getUserAddress().getPost());
//        assertEquals(UserRole.BASIC_USER, userInfo.getRole());
//        assertEquals(UserStatus.REGISTERED, userInfo.getStatus());
//        assertThat(userInfo.getRegisteredAt()).isBefore(LocalDateTime.now());
//
//    }
//
//}

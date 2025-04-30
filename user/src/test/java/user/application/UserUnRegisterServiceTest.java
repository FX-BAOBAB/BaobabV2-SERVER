//package user.application;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import config.AcceptanceTestWithMongo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import user.adapter.output.persistence.enums.UserStatus;
//import user.adapter.output.persistence.repository.UserDocument;
//import user.application.port.output.UserPersistencePort;
//import user.domain.command.UserUnRegisterCommand;
//import user.utils.UserRegisterUtils;
//
//@SpringBootTest
//class UserUnRegisterServiceTest extends AcceptanceTestWithMongo {
//
//    @Autowired
//    private UserUnRegisterService userUnRegisterService;
//
//    @Autowired
//    private UserPersistencePort userPersistencePort;
//
//    @Autowired
//    private UserRegisterUtils userRegisterUtils;
//
//    @Test
//    void 회원탈퇴_성공() {
//
//        // Given
//        userRegisterUtils.registerUser("test@example.com", "Password12@");
//        UserDocument userDocument = userPersistencePort.getUserDocumentBy("test@example.com",
//            UserStatus.REGISTERED);
//        UserUnRegisterCommand userUnRegisterCommand = getUserUnRegisterCommand(userDocument.getId());
//
//        // When
//        boolean isUnRegistered = userUnRegisterService.unRegister(userUnRegisterCommand);
//
//        // Then
//        assertTrue(isUnRegistered);
//
//    }
//
//    private UserUnRegisterCommand getUserUnRegisterCommand(String userId) {
//        return UserUnRegisterCommand.builder().userId(userId).build();
//    }
//
//}

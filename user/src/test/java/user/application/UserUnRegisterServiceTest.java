package user.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import config.AcceptanceTestWithMongo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import user.domain.command.UserUnRegisterCommand;
import user.utils.UserRegisterUtils;

@SpringBootTest
class UserUnRegisterServiceTest extends AcceptanceTestWithMongo {

    @Autowired
    private UserUnRegisterService userUnRegisterService;

    @Autowired
    private UserRegisterUtils userRegisterUtils;

    @Test
    void 회원탈퇴_성공() {

        // Given
        String userId = userRegisterUtils.registerUser("test@example.com", "Password12@");
        UserUnRegisterCommand userUnRegisterCommand = getUserUnRegisterCommand(userId);

        // When
        boolean isUnRegistered = userUnRegisterService.unRegister(userUnRegisterCommand);

        // Then
        assertTrue(isUnRegistered);

    }

    private UserUnRegisterCommand getUserUnRegisterCommand(String userId) {
        return UserUnRegisterCommand.builder().userId(userId).build();
    }

}

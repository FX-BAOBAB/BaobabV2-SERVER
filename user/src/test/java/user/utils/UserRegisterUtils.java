package user.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.UserRegisterService;
import user.domain.command.UserRegisterCommand;

@Component
public class UserRegisterUtils {

    @Autowired
    private UserRegisterService userRegisterService;

    public String registerUser(String email, String password) {

        UserRegisterCommand userRegisterCommand = UserRegisterCommand.builder()
            .email(email)
            .password(password)
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

        return userRegisterService.register(userRegisterCommand);
    }

}

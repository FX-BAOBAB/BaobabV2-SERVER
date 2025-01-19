package user.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import user.application.UserLoginService;
import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;

@Component
public class UserLoginUtils {

    @Autowired
    private UserLoginService userLoginService;

    public TokenCommand loginUser(String email, String password) {
        UserLoginCommand userLoginCommand = UserLoginCommand.builder()
            .email(email)
            .password(password)
            .build();
        return userLoginService.login(userLoginCommand);
    }

}

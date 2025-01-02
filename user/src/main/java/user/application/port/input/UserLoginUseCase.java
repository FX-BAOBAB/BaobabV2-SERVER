package user.application.port.input;

import user.domain.command.UserLoginCommand;
import user.domain.command.TokenCommand;

public interface UserLoginUseCase {

    TokenCommand login(UserLoginCommand userLoginCommand);

}

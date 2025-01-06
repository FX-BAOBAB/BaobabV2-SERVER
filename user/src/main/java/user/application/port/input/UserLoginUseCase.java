package user.application.port.input;

import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;

public interface UserLoginUseCase {

    TokenCommand login(UserLoginCommand userLoginCommand);

}

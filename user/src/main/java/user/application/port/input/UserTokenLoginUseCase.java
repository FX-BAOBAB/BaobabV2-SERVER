package user.application.port.input;

import user.domain.command.UserLoginCommand;
import user.domain.command.TokenCommand;

public interface UserTokenLoginUseCase {

    TokenCommand tokenLogin(UserLoginCommand userLoginCommand);

}

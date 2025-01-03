package user.application.port.input;

import user.domain.command.AccountRegisterCommand;
import user.domain.command.UserRegisterCommand;

public interface UserRegisterUseCase {

    boolean register(UserRegisterCommand userRegisterCommand, AccountRegisterCommand accountRegisterCommand);

}

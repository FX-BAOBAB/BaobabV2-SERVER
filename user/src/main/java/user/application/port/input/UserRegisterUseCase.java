package user.application.port.input;

import user.domain.command.UserRegisterCommand;

public interface UserRegisterUseCase {

    String register(UserRegisterCommand userRegisterCommand);

}
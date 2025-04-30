package user.application.port.input;

import user.domain.command.UserRegisterCommand;

public interface UserRegisterUseCase {

    Boolean register(UserRegisterCommand userRegisterCommand);

}
package user.application.port.output;

import user.domain.command.UserRegisterCommand;

public interface UserRegisterPort {

    UserRegisterCommand save(UserRegisterCommand userRegisterCommand);

}

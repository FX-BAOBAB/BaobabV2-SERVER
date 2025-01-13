package user.application.port.input;

import user.domain.command.UserUnRegisterCommand;

public interface UserUnRegisterUseCase {

    boolean unRegister(UserUnRegisterCommand userUnRegisterCommand);

}

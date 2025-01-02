package user.application.port.input;

import user.domain.command.UserUpdateCommand;

public interface UserUpdateUseCase {

    boolean update(UserUpdateCommand userUpdateCommand);

}

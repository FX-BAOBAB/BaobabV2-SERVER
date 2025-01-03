package user.application.port.output;

import user.adapter.output.persistence.enums.UserStatus;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.command.UserUpdateCommand;

public interface UserPersistencePort {

    boolean checkEmailDuplicate(String email);

    boolean checkNickNameDuplicate(String name);

    boolean save(UserRegisterCommand userRegisterCommand);

    boolean update(UserUpdateCommand userUpdateCommand);

    UserReaderCommand getUserInfoBy(String userId, UserStatus status);

}

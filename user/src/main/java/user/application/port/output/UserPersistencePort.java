package user.application.port.output;

import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.User;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.UserDto;

public interface UserPersistencePort {

    boolean checkEmailDuplicate(String email);

    boolean checkNickNameDuplicate(String name);

    boolean saveUser(UserRegisterCommand userRegisterCommand);

    boolean saveUser(User user);

    UserDto getUserInfoBy(String userId, UserStatus status);

    UserReaderCommand getUserInfo(String email, UserStatus status);

    void setLastLoginAt(UserReaderCommand userReaderCommand);

}
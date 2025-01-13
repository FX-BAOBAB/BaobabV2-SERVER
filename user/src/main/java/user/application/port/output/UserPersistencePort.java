package user.application.port.output;

import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.command.UserReaderCommand;
import user.domain.dto.UserRegisterForm;
import user.domain.dto.UserUnRegisterForm;
import user.domain.dto.UserUpdateForm;

public interface UserPersistencePort {

    boolean checkEmailDuplicate(String email);

    boolean checkNickNameDuplicate(String name);

    boolean saveUser(UserRegisterForm userRegisterForm);

    boolean updateUser(UserUpdateForm userUpdateForm);

    UserDocument getUserDocument(String userId, UserStatus status);

    UserReaderCommand getUserInfo(String email, UserStatus status);

    void setLastLoginAt(UserReaderCommand userReaderCommand);

    boolean unRegisterUser(UserUnRegisterForm userUnRegisterForm);

}
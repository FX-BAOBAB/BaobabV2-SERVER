package user.application.port.output;

import java.time.LocalDateTime;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.command.UserReaderCommand;
import user.domain.dto.ProfileImage;
import user.domain.form.UserRegisterForm;
import user.domain.form.UserUnRegisterForm;
import user.domain.form.UserUpdateForm;

public interface UserPersistencePort {

    boolean checkEmailDuplicate(String email);

    boolean checkNickNameDuplicate(String name);

    String saveUser(UserRegisterForm userRegisterForm);

    Boolean saveProfileImage(String userId, ProfileImage profileImage);

    boolean updateUser(UserUpdateForm userUpdateForm);

    UserDocument getUserDocument(String userId, UserStatus status);

    UserDocument getUserDocumentBy(String email, UserStatus status);

    UserReaderCommand getUserInfo(String userId, UserStatus status);

    void setLastLoginAt(String userId, LocalDateTime lastLoginAt);

    boolean unRegisterUser(UserUnRegisterForm userUnRegisterForm);

    UserReaderCommand getUserInfo(String userId);
}

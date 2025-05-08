package user.adapter.output.persistence;

import global.annotation.output.PersistenceAdapter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.application.port.output.UserPersistencePort;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.user.UserNotFoundException;
import user.domain.command.UserReaderCommand;
import user.domain.dto.ProfileImage;
import user.domain.form.DormantUserSaveForm;
import user.domain.form.UserRegisterForm;
import user.domain.form.UserUnRegisterForm;
import user.domain.form.UserUpdateForm;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserMongoRepository userMongoRepository;

    @Override
    public boolean checkEmailDuplicate(String email) {
        return userMongoRepository.existsByUserAccount_Email(email);
    }

    @Override
    public boolean checkNickNameDuplicate(String nickName) {
        return userMongoRepository.existsByNickName(nickName);
    }

    @Override
    public String saveUser(UserRegisterForm userRegisterForm) {
        UserDocument user = UserDocument.of(userRegisterForm);
        UserDocument savedUser = userMongoRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public Boolean saveProfileImage(String userId, ProfileImage profileImage) {
        UserDocument userDocument = getUserDocument(userId, UserStatus.REGISTERED);
        userDocument.setProfileImage(profileImage);
        UserDocument savedUser = userMongoRepository.save(userDocument);
        return savedUser.getId() != null;
    }

    @Override
    public boolean updateUser(UserUpdateForm userUpdateForm) {
        UserDocument updatedDocument = UserDocument.of(userUpdateForm);
        UserDocument savedUser = userMongoRepository.save(updatedDocument);
        return savedUser.getId() != null;
    }

    /**
     * 관리자 or Server 내에서 User 정보 참조할 때 사용
     *
     * @param userId
     * @param status
     * @return
     */
    @Override
    public UserDocument getUserDocument(String userId, UserStatus status) {
        return userMongoRepository.findFirstByIdAndStatusOrderByIdDesc(userId, status)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * 관리자 or Server 내에서 User 정보 참조할 때 사용
     *
     * @param email
     * @param status
     * @return
     */
    @Override
    public UserDocument getUserDocumentBy(String email, UserStatus status) {
        return userMongoRepository.findFirstByUserAccount_EmailAndStatusOrderByIdDesc(email, status)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserReaderCommand getUserInfo(String userId, UserStatus status) {
        UserDocument user = userMongoRepository.findFirstByIdAndStatusOrderByIdDesc(userId, status)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        return UserReaderCommand.of(user);
    }

    @Override
    public UserReaderCommand getUserInfo(String userId) {
        UserDocument user = userMongoRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        return UserReaderCommand.of(user);
    }

    @Override
    public void setLastLoginAt(String userId, LocalDateTime lastLoginAt) {
        UserDocument user = userMongoRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId, UserStatus.REGISTERED)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        user.setLastLoginAt(lastLoginAt);
        userMongoRepository.save(user);
    }

    @Override
    public boolean unRegisterUser(UserUnRegisterForm userUnRegisterForm) {
        UserDocument userDocument = getUserDocument(userUnRegisterForm.getUserId(),
            UserStatus.REGISTERED);

        userDocument.setId(userUnRegisterForm.getUserId());
        userDocument.setUnRegisteredAt(userUnRegisterForm.getUnRegisterAt());
        userDocument.setStatus(userUnRegisterForm.getStatus());

        UserDocument savedUser = userMongoRepository.save(userDocument);
        return savedUser.getId() != null;
    }

    @Override
    public void saveDormantUser(DormantUserSaveForm dormantUserSaveForm) {
        userMongoRepository.save(UserDocument.of(dormantUserSaveForm));
    }

}

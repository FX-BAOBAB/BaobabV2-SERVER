package user.adapter.output.persistence;

import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.User;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.application.port.output.UserPersistencePort;
import user.core.common.converter.UserConverter;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.token.UserNotFoundException;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.command.UserUpdateCommand;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserMongoRepository userMongoRepository;
    private final UserConverter userConverter;

    @Override
    public boolean checkEmailDuplicate(String email) {
        return userMongoRepository.existsByAccount_Email(email);
    }

    @Override
    public boolean checkNickNameDuplicate(String nickName) {
        return userMongoRepository.existsByNickName(nickName);
    }

    @Override
    public boolean saveUser(UserRegisterCommand userRegisterCommand) {
        User user = userConverter.toUser(userRegisterCommand);
        User savedUser = userMongoRepository.save(user);
        return savedUser.getId() != null;
    }

    @Override
    public boolean updateUser(UserReaderCommand userReaderCommand) {
        User user = userConverter.toUser(userReaderCommand);
        User savedUser = userMongoRepository.save(user);
        return savedUser.getId() != null;
    }

    @Override
    public UserReaderCommand getUserInfoBy(String userId, UserStatus status) {
        User user = userMongoRepository.findFirstByIdAndStatusOrderByIdDesc(userId, status)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        return userConverter.toReaderCommand(user);
    }

    @Override
    public UserReaderCommand getUserInfo(String email, UserStatus status) {
        User user = userMongoRepository.findFirstByAccount_EmailAndStatusOrderByIdDesc(email, status)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        return userConverter.toReaderCommand(user);
    }

    @Override
    public void setLastLoginAt(UserReaderCommand userReaderCommand) {
        User user = userMongoRepository.findFirstByIdAndStatusOrderByIdDesc(
                userReaderCommand.getUserId(), userReaderCommand.getStatus())
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        user.setLastLoginAt(userReaderCommand.getLastLoginAt());
        userMongoRepository.save(user);
    }

}

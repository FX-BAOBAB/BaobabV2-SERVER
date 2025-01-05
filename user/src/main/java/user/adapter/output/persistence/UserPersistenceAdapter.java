package user.adapter.output.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.User;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.application.port.output.UserPersistencePort;
import user.core.common.converter.UserConverter;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.command.UserUpdateCommand;

@Component // TODO @OutputAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserMongoRepository userMongoRepository;
    private final UserConverter userConverter;

    @Override
    public boolean checkEmailDuplicate(String email) {
        return userMongoRepository.existsByEmail(email);
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
    public boolean updateUser(UserUpdateCommand userUpdateCommand) {
        return false;
    }

    @Override
    public UserReaderCommand getUserInfoBy(String userId, UserStatus status) {
        User user = userMongoRepository.findFirstByIdAndStatusOrderByIdDesc(userId, status)
            .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않음"));
        return userConverter.toReaderCommand(user);
    }

    @Override
    public UserReaderCommand getUserInfo(String email, UserStatus status) {
        User user = userMongoRepository.findFirstByEmailAndStatusOrderByIdDesc(email, status)
            .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않음"));
        return userConverter.toReaderCommand(user);
    }

    @Override
    public void setLastLoginAt(UserReaderCommand userReaderCommand) {
        User user = userMongoRepository.findFirstByIdAndStatusOrderByIdDesc(
                userReaderCommand.getUserId(), userReaderCommand.getStatus())
            .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않음"));
        user.setLastLoginAt(userReaderCommand.getLastLoginAt());
        userMongoRepository.save(user);
    }

}

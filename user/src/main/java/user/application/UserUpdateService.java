package user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.input.UserUpdateUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserUpdateCommand;

@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserUpdateUseCase {

    private final UserPersistencePort userPersistencePort;

    @Override
    public boolean updateUser(UserUpdateCommand userUpdateCommand) {
        UserReaderCommand userInfo = userPersistencePort.getUserInfoBy(
            userUpdateCommand.getUserId(), UserStatus.REGISTERED);

        updateUserInfo(userUpdateCommand, userInfo);

        boolean isUpdated = userPersistencePort.updateUser(userInfo);
        return isUpdated;
    }

    private static void updateUserInfo(UserUpdateCommand userUpdateCommand, UserReaderCommand userInfo) {
        userInfo.setUserId(userUpdateCommand.getUserId());
        userInfo.setPassword(userUpdateCommand.getPassword());
        userInfo.setNickName(userUpdateCommand.getNickName());
        userInfo.setName(userUpdateCommand.getName());
        userInfo.setPhone(userUpdateCommand.getPhone());
        userInfo.setBirth(userUpdateCommand.getBirth());
        userInfo.setDepartment(userUpdateCommand.getDepartment());
        userInfo.setAddress(userUpdateCommand.getAddress());
        userInfo.setBasicAddress(userUpdateCommand.getBasicAddress());
        userInfo.setDetailAddress(userUpdateCommand.getDetailAddress());
        userInfo.setPost(userUpdateCommand.getPost());
        userInfo.setImageId(userUpdateCommand.getImageId());
    }


}

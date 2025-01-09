package user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.User;
import user.application.port.input.UserUpdateUseCase;
import user.application.port.output.UserPersistencePort;
import user.core.common.converter.UserConverter;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.command.UserUpdateCommand;
import user.domain.dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserUpdateUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserConverter userConverter;

    @Override
    public boolean updateUser(UserUpdateCommand userUpdateCommand) {
        UserDto userDto = userPersistencePort.getUserInfoBy(
            userUpdateCommand.getUserId(), UserStatus.REGISTERED);

        updateUserInfo(userUpdateCommand, userDto);
        User user = userConverter.toUser(userDto);

        boolean isUpdated = userPersistencePort.saveUser(user);
        return isUpdated;
    }

    private static void updateUserInfo(UserUpdateCommand userUpdateCommand, UserDto userDto) {
        userDto.setUserId(userUpdateCommand.getUserId());
        userDto.setPassword(userUpdateCommand.getPassword());
        userDto.setNickName(userUpdateCommand.getNickName());
        userDto.setName(userUpdateCommand.getName());
        userDto.setPhone(userUpdateCommand.getPhone());
        userDto.setBirth(userUpdateCommand.getBirth());
        userDto.setDepartment(userUpdateCommand.getDepartment());
        userDto.setAddress(userUpdateCommand.getAddress());
        userDto.setBasicAddress(userUpdateCommand.getBasicAddress());
        userDto.setDetailAddress(userUpdateCommand.getDetailAddress());
        userDto.setPost(userUpdateCommand.getPost());
        userDto.setImageId(userUpdateCommand.getImageId());
    }

}

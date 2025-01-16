package user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.application.port.input.UserRegisterUseCase;
import user.application.port.output.UserPersistencePort;
import user.core.common.converter.UserConverter;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserRegisterForm;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserConverter userConverter;

    @Override
    public String register(UserRegisterCommand userRegisterCommand) {
        var target = initUserParameter(userRegisterCommand);
        return userPersistencePort.saveUser(target);
    }

    @Override
    public Boolean registerProfileImage(String userId, ProfileImage profileImage) {
        return userPersistencePort.saveProfileImage(userId, profileImage);
    }

    private UserRegisterForm initUserParameter(UserRegisterCommand userRegisterCommand) {
        return userConverter.toUserRegisterForm(userRegisterCommand);
    }

    
}

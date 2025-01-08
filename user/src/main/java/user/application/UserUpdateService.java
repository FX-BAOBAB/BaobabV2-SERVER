package user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.application.port.input.UserUpdateUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserUpdateCommand;

@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserUpdateUseCase {

    private final UserPersistencePort userPersistencePort;

    @Override
    public boolean updateUser(UserUpdateCommand userUpdateCommand) {
        boolean isUpdated = userPersistencePort.updateUser(userUpdateCommand);
        return isUpdated;
    }
}

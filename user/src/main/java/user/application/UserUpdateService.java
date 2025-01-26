package user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.application.port.input.UserUpdateUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserUpdateCommand;
import user.domain.dto.UserUpdateForm;

@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserUpdateUseCase {

    private final UserPersistencePort userPersistenceAdapter;

    @Override
    public boolean updateUserInfo(UserUpdateCommand userUpdateCommand) {
        UserUpdateForm userUpdateForm = UserUpdateForm.toForm(userUpdateCommand);
        return userPersistenceAdapter.updateUser(userUpdateForm);
    }

}

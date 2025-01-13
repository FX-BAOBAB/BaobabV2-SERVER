package user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.application.port.input.UserUnRegisterUseCase;
import user.application.port.output.UserPersistencePort;
import user.core.common.converter.UserConverter;
import user.domain.command.UserUnRegisterCommand;
import user.domain.dto.UserUnRegisterForm;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUnRegisterService implements UserUnRegisterUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean unRegister(UserUnRegisterCommand userUnRegisterCommand) {

        UserUnRegisterForm unRegisterForm = userConverter.toUnregisterForm(
            userUnRegisterCommand.getUserId());

        return userPersistencePort.unRegisterUser(unRegisterForm);
    }
}

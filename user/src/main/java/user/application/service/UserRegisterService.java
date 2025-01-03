package user.application.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.input.UserRegisterUseCase;
import user.application.port.output.AccountRegisterPort;
import user.application.port.output.UserRegisterPort;
import user.domain.command.AccountRegisterCommand;
import user.domain.command.UserRegisterCommand;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {

    private final UserRegisterPort userRegisterPort;
    private final AccountRegisterPort accountRegisterPort;

    @Override
    public boolean register(
        UserRegisterCommand userRegisterCommand,
        AccountRegisterCommand accountRegisterCommand
    ) {
        userRegisterCommand.setRole(UserRole.BASIC_USER);
        userRegisterCommand.setRegisteredAt(LocalDateTime.now());
        userRegisterCommand.setStatus(UserStatus.REGISTERED);

        UserRegisterCommand registeredCommand = userRegisterPort.save(userRegisterCommand);
        accountRegisterCommand.setUserId(registeredCommand.getId());
        return accountRegisterPort.save(accountRegisterCommand);
    }

}


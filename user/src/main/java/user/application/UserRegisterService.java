package user.application;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.input.UserRegisterUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserRegisterCommand;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean register(UserRegisterCommand userRegisterCommand) {
        initializeUser(userRegisterCommand);
        boolean isRegistered = userPersistencePort.saveUser(userRegisterCommand);
        return isRegistered;
    }

    private void initializeUser(UserRegisterCommand userRegisterCommand) {
        userRegisterCommand.setPassword(passwordEncoder.encode(userRegisterCommand.getPassword()));
        userRegisterCommand.setRegisteredAt(LocalDateTime.now());
        userRegisterCommand.setRole(UserRole.BASIC_USER);
        userRegisterCommand.setStatus(UserStatus.REGISTERED);
    }
    
}

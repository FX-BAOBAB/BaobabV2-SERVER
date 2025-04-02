package user.application;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import user.application.port.input.UserRegisterUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserRegisterCommand;
import user.domain.form.UserRegisterForm;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {

    private final UserPersistencePort userPersistencePort;

    @Override
    public String register(UserRegisterCommand userRegisterCommand) {

        // 비밀번호 암호화
        String encryptedPassword = BCrypt.hashpw(userRegisterCommand.getUserAccount().getPassword(),
            BCrypt.gensalt());

        return userPersistencePort.saveUser(UserRegisterForm.of(userRegisterCommand, encryptedPassword));
    }

}

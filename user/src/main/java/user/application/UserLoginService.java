package user.application;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.input.UserLoginUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;
import user.domain.command.UserReaderCommand;
import user.security.jwt.service.TokenIssueService;

@Service
@RequiredArgsConstructor
public class UserLoginService implements UserLoginUseCase {

    private final TokenIssueService tokenIssueService;
    private final UserPersistencePort userPersistencePort;

    @Override
    public TokenCommand login(UserLoginCommand userLoginCommand) {

        UserReaderCommand userInfo = userPersistencePort.getUserInfo(userLoginCommand.getEmail(),
            UserStatus.REGISTERED);

        if(BCrypt.checkpw(userLoginCommand.getPassword(), userInfo.getPassword())) {
            userInfo.setLastLoginAt(LocalDateTime.now());
            userPersistencePort.setLastLoginAt(userInfo);
        }

        return tokenIssueService.issueToken(userInfo.getUserId());
    }

}

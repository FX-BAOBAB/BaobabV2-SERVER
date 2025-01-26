package user.application;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.application.port.input.UserLoginUseCase;
import user.application.port.output.UserPersistencePort;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.user.PasswordMismatchException;
import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;
import user.security.jwt.model.JwtInfoDto;
import user.security.jwt.service.TokenIssueService;

@Service
@RequiredArgsConstructor
public class UserLoginService implements UserLoginUseCase {

    private final TokenIssueService tokenIssueService;
    private final UserPersistencePort userPersistencePort;

    @Override
    public TokenCommand login(UserLoginCommand userLoginCommand) {

        UserDocument userDocument = userPersistencePort.getUserDocumentBy(
            userLoginCommand.getEmail(), UserStatus.REGISTERED);

        if(!BCrypt.checkpw(userLoginCommand.getPassword(), userDocument.getAccount().getPassword())) {
            throw new PasswordMismatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

        LocalDateTime lastLoginAt = LocalDateTime.now();
        userPersistencePort.setLastLoginAt(userDocument.getId(), lastLoginAt);

        JwtInfoDto jwtInfoDto = JwtInfoDto.toTokenInfo(userDocument);

        return tokenIssueService.issueToken(jwtInfoDto);
    }

}

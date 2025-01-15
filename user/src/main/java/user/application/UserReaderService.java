package user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.input.UserReaderUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserReaderCommand;

@Service
@RequiredArgsConstructor
public class UserReaderService implements UserReaderUseCase {

    private final UserPersistencePort userPersistencePort;

    @Override
    public UserReaderCommand getUserInfoBy(String userId) {
        return userPersistencePort.getUserInfo(userId, UserStatus.REGISTERED);
    }

}

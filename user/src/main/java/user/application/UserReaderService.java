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

    private static final String UNREGISTERED_USER_NICKNAME = "탈퇴한 사용자";

    @Override
    public UserReaderCommand getUserInfoBy(String userId) {
        return userPersistencePort.getUserInfo(userId, UserStatus.REGISTERED);
    }

    @Override
    public String getUserNickname(String userId) {
        String nickname = userPersistencePort.getUserInfo(userId, UserStatus.REGISTERED).getNickName();
        return nickname == null ? UNREGISTERED_USER_NICKNAME : nickname;
    }

}

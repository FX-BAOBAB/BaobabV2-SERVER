package user.adapter.output;

import global.annotation.PersistenceAdapter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import user.adapter.output.persistence.AccountMongoRepository;
import user.adapter.output.persistence.User;
import user.adapter.output.persistence.UserMongoRepository;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.output.UserRegisterPort;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRegisterPort {

    private final UserMongoRepository userMongoRepository;

    @Override
    public User save(User user) {
        user.setStatus(UserStatus.REGISTERED);
        user.setRole(UserRole.BASIC_USER);
        user.setRegisteredAt(LocalDateTime.now());
        return userMongoRepository.save(user);
    }

}

package user.adapter.output;

import global.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import user.adapter.output.persistence.User;
import user.adapter.output.persistence.UserMongoRepository;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.output.UserReaderPort;
import user.application.port.output.UserRegisterPort;
import user.domain.command.UserRegisterCommand;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRegisterPort, UserReaderPort {

    private final UserMongoRepository userMongoRepository;

    @Override
    public UserRegisterCommand save(UserRegisterCommand userRegisterCommand) {
        User user = User.builder()
            .name(userRegisterCommand.getName())
            .birth(userRegisterCommand.getBirth())
            .department(userRegisterCommand.getDepartment())
            .address(userRegisterCommand.getAddress())
            .detailAddress(userRegisterCommand.getDetailAddress())
            .basicAddress(userRegisterCommand.getBasicAddress())
            .post(userRegisterCommand.getPost())
            .imageId(userRegisterCommand.getImageId())
            .role(userRegisterCommand.getRole())
            .status(userRegisterCommand.getStatus())
            .registeredAt(userRegisterCommand.getRegisteredAt())
            .build();
        User savedUser = userMongoRepository.save(user);
        return UserRegisterCommand.builder()
            .id(savedUser.getId())
            .build();
    }

    @Override
    public User findByUserIdAndStatus(String userId, UserStatus status) {
        return null;
    }
}

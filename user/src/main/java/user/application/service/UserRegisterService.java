package user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.Account;
import user.application.port.input.UserRegisterUseCase;
import user.application.port.output.AccountRegisterPort;
import user.application.port.output.UserRegisterPort;
import user.adapter.output.persistence.User;
import user.domain.command.UserRegisterCommand;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {

    private final UserRegisterPort userRegisterPort;
    private final AccountRegisterPort accountRegisterPort;

    @Override
    public boolean register(UserRegisterCommand userRegisterCommand) {
        User savedUser = userRegisterPort.save(User.builder()
            .nickName(userRegisterCommand.getNickName())
            .birth(userRegisterCommand.getBirth())
            .department(userRegisterCommand.getDepartment())
            .address(userRegisterCommand.getAddress())
            .detailAddress(userRegisterCommand.getDetailAddress())
            .basicAddress(userRegisterCommand.getBasicAddress())
            .post(userRegisterCommand.getPost())
            .imageId(userRegisterCommand.getImageId())
            .build());

        return accountRegisterPort.save(Account.builder()
                .email(userRegisterCommand.getEmail())
                .password(userRegisterCommand.getPassword())
                .name(userRegisterCommand.getName())
                .userId(savedUser.getId())
            .build());
    }

}


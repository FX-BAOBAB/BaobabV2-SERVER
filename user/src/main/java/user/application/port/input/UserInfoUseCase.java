package user.application.port.input;

import user.domain.command.UserInfoCommand;

public interface UserInfoUseCase {

    UserInfoCommand findByUserId(String userId);

}

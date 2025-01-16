package user.application.port.input;

import user.domain.command.UserRegisterCommand;
import user.domain.dto.ProfileImage;

public interface UserRegisterUseCase {

    String register(UserRegisterCommand userRegisterCommand);

    Boolean registerProfileImage(String userId, ProfileImage profileImage);

}
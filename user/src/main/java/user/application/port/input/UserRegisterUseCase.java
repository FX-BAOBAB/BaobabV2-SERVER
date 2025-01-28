package user.application.port.input;

import org.springframework.web.multipart.MultipartFile;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.ProfileImage;

public interface UserRegisterUseCase {

    String register(UserRegisterCommand userRegisterCommand);

    Boolean registerProfileImage(String userId, MultipartFile profileImage);

}
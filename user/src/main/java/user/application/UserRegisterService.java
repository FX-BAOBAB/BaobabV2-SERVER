package user.application;

import file.application.port.input.ImageMetaDataUseCase;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import user.application.port.input.UserRegisterUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.ProfileImage;
import user.domain.form.UserRegisterForm;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {

    private final UserPersistencePort userPersistencePort;
    private final ImageMetaDataUseCase imageMetaDataUseCase;

    @Override
    public String register(UserRegisterCommand userRegisterCommand) {

        // 비밀번호 암호화
        String encryptedPassword = BCrypt.hashpw(userRegisterCommand.getUserAccount().getPassword(),
            BCrypt.gensalt());

        return userPersistencePort.saveUser(UserRegisterForm.of(userRegisterCommand, encryptedPassword));
    }

    @Override
    public Boolean registerProfileImage(String userId, MultipartFile profileImage) {
        ProfileImage savedProfileImage = saveProfileImage(userId, profileImage);
        return userPersistencePort.saveProfileImage(userId, savedProfileImage);
    }

    private ProfileImage saveProfileImage(String userId, MultipartFile profileImage) {
        ImageMetaData imageMetaData = imageMetaDataUseCase.processImageMetaData(ImageKind.USER,
            userId, profileImage);
        return ProfileImage.builder()
            .ImageId(imageMetaData.getId())
            .ImageUrl(imageMetaData.getUrl())
            .build();
    }

}

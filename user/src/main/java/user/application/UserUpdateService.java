package user.application;

import file.application.port.input.ImageMetaDataUseCase;
import file.application.port.input.ImageStorageUseCase;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import user.application.port.input.UserUpdateUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserUpdateCommand;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserUpdateForm;

@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserUpdateUseCase {

    private final UserPersistencePort userPersistenceAdapter;
    private final ImageMetaDataUseCase imageMetaDataUseCase;
    private final ImageStorageUseCase imageStorageUseCase;

    @Override
    public boolean updateUserInfo(UserUpdateCommand userUpdateCommand) {

        ProfileImage profileImage = saveProfileImage(userUpdateCommand.getUserId(),
            userUpdateCommand.getProfileImage());
        deleteProfileImage(userUpdateCommand);

        UserUpdateForm userUpdateForm = UserUpdateForm.toForm(userUpdateCommand, profileImage);
        return userPersistenceAdapter.updateUser(userUpdateForm);
    }

    private void deleteProfileImage(UserUpdateCommand userUpdateCommand) {
        Optional.ofNullable(userUpdateCommand.getDeleteImageId())
            .ifPresent(imageStorageUseCase::deleteImage);
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

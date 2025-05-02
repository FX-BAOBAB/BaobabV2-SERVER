package user.application;

import file.application.port.input.ImageMetaDataUseCase;
import file.application.port.input.ImageStorageUseCase;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.application.port.input.UserUpdateUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserUpdateCommand;
import user.domain.dto.ProfileImage;
import user.domain.form.UserUpdateForm;

@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserUpdateUseCase {

    private final UserPersistencePort userPersistenceAdapter;
    private final ImageMetaDataUseCase imageMetaDataUseCase;
    private final ImageStorageUseCase imageStorageUseCase;


    @Override
    public boolean updateUserInfo(UserUpdateCommand userUpdateCommand) {
        UserDocument userDocument = userPersistenceAdapter.getUserDocument(
            userUpdateCommand.getUserId(), UserStatus.REGISTERED);

        // userDocument.getProfileImage() : nullable
        ProfileImage profileImage = processProfileImage(userUpdateCommand, userDocument.getProfileImage());
        userDocument.setProfileImage(profileImage);

        UserUpdateForm userUpdateForm = UserUpdateForm.toForm(userUpdateCommand, userDocument);
        return userPersistenceAdapter.updateUser(userUpdateForm);
    }

    /**
     * @param command
     * @param existingProfileImage
     * @return nullable
     */
    private ProfileImage processProfileImage(UserUpdateCommand command, ProfileImage existingProfileImage) {
        MultipartFile newProfileImage = command.getProfileImage();

        if (newProfileImage != null) {
            Optional.ofNullable(existingProfileImage)
                .map(ProfileImage::getImageId)
                .ifPresent(imageStorageUseCase::deleteImage);

            ImageMetaData imageMetaData = imageMetaDataUseCase.processImageMetaData(
                ImageKind.USER, command.getUserId(), newProfileImage);

            return ProfileImage.builder()
                .imageId(imageMetaData.getId())
                .imageUrl(imageMetaData.getUrl())
                .build();
        }

        return existingProfileImage;
    }

}

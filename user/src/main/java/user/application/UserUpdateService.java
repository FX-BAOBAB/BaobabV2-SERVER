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
import user.domain.dto.UserUpdateForm;

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

        ProfileImage profileImage = saveProfileImage(userUpdateCommand,
            userDocument.getProfileImage());
        deleteProfileImage(userUpdateCommand);

        userDocument.setProfileImage(profileImage);

        UserUpdateForm userUpdateForm = UserUpdateForm.toForm(userUpdateCommand, userDocument);
        return userPersistenceAdapter.updateUser(userUpdateForm);
    }

    private void deleteProfileImage(UserUpdateCommand userUpdateCommand) {
        Optional.ofNullable(userUpdateCommand.getDeleteImageId())
            .ifPresent(imageStorageUseCase::deleteImage);
    }

    private ProfileImage saveProfileImage(UserUpdateCommand userUpdateCommand,
        ProfileImage existingProfileImage) {
        return Optional.ofNullable(userUpdateCommand.getProfileImage())
            .filter(image -> !image.isEmpty())
            .map(image -> {
                ImageMetaData imageMetaData = imageMetaDataUseCase.processImageMetaData(
                    ImageKind.USER, userUpdateCommand.getUserId(), image);
                return ProfileImage.builder()
                    .ImageId(imageMetaData.getId())
                    .ImageUrl(imageMetaData.getUrl())
                    .build();
            })
            .orElse(existingProfileImage); // 새로운 이미지가 없으면 기존 이미지를 유지
    }
}

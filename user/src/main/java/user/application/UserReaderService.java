package user.application;

import file.core.DefaultImageService;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.input.UserReaderUseCase;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserReaderCommand;
import user.domain.dto.ProfileImage;

@Service
@RequiredArgsConstructor
public class UserReaderService implements UserReaderUseCase {

    private final UserPersistencePort userPersistencePort;
    private final DefaultImageService defaultImageService;

    private static final String UNREGISTERED_USER_NICKNAME = "탈퇴한 사용자";

    @Override
    public UserReaderCommand getUserInfoBy(String userId) {
        UserReaderCommand userInfo = userPersistencePort.getUserInfo(userId, UserStatus.REGISTERED);
        setProfileImageIfAbsent(userInfo);
        return userInfo;
    }

    @Override
    public UserReaderCommand getUserInfoWithUnregisteredNickname(String userId) {
        UserReaderCommand user = userPersistencePort.getUserInfo(userId);
        setProfileImageIfAbsent(user);
        if (user.getStatus().equals(UserStatus.UNREGISTERED)) {
            user.setNickName(UNREGISTERED_USER_NICKNAME);
        }

        return user;
    }

    private void setProfileImageIfAbsent(UserReaderCommand user) {
        if (user.getProfileImage() == null) {
            ImageMetaData defaultImage = defaultImageService.getDefaultImage(ImageKind.USER_DEFAULT);

            if (defaultImage == null) {
                return; // 기본 이미지가 서버에 설정되지 않은 경우
            }

            user.setProfileImage(ProfileImage.builder()
                .ImageId(defaultImage.getId())
                .ImageUrl(defaultImage.getUrl())
                .build());
        }
    }

}

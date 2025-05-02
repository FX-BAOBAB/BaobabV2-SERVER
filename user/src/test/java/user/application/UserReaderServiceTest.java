package user.application;

import file.core.DefaultImageService;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserReaderCommand;
import user.domain.dto.ProfileImage;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserReaderServiceTest {

    @Mock
    private UserPersistencePort userPersistencePort;
    @Mock
    private DefaultImageService defaultImageService;

    @InjectMocks
    private UserReaderService userReaderService;

    @Captor
    private ArgumentCaptor<String> userIdCaptor;

    @Test
    void 사용자_조회_프로필이미지없으면_기본이미지설정_성공() {

        // Given
        String userId = "user123";
        UserReaderCommand userWithoutProfileImage = UserReaderCommand.builder()
            .userId(userId)
            .profileImage(null)
            .build();

        ImageMetaData defaultImage = ImageMetaData.builder()
            .id("default-id")
            .url("http://default-image")
            .kind(ImageKind.USER_DEFAULT)
            .build();

        when(userPersistencePort.getUserInfo(userId, UserStatus.REGISTERED)).thenReturn(
            userWithoutProfileImage);
        when(defaultImageService.getDefaultImage(ImageKind.USER_DEFAULT)).thenReturn(defaultImage);

        // When
        UserReaderCommand result = userReaderService.getUserInfoBy(userId);

        // Then
        verify(userPersistencePort, times(1)).getUserInfo(userIdCaptor.capture(),
            eq(UserStatus.REGISTERED));
        verify(defaultImageService, times(1)).getDefaultImage(ImageKind.USER_DEFAULT);

        assertThat(userIdCaptor.getValue()).isEqualTo(userId);

        assertThat(result.getProfileImage()).isNotNull();
        assertThat(result.getProfileImage().getImageUrl()).isEqualTo("http://default-image");
    }

    @Test
    void 사용자_조회_프로필이미지있으면_기본이미지설정안함_성공() {

        // Given
        String userId = "user123";
        UserReaderCommand userWithoutProfileImage = UserReaderCommand.builder()
            .userId(userId)
            .profileImage(ProfileImage.builder()
                .imageId("existing-image-id")
                .imageUrl("http://existing-image")
                .build())
            .build();

        when(userPersistencePort.getUserInfo(userId, UserStatus.REGISTERED)).thenReturn(
            userWithoutProfileImage);

        // When
        UserReaderCommand result = userReaderService.getUserInfoBy(userId);

        // Then
        verify(userPersistencePort, times(1)).getUserInfo(userIdCaptor.capture(),
            eq(UserStatus.REGISTERED));
        verify(defaultImageService, never()).getDefaultImage(ImageKind.USER_DEFAULT);

        assertThat(userIdCaptor.getValue()).isEqualTo(userId);

        assertThat(result.getProfileImage()).isNotNull();
        assertThat(result.getProfileImage().getImageUrl()).isEqualTo("http://existing-image");
    }

}

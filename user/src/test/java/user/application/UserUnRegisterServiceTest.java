package user.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserUnRegisterCommand;
import user.domain.form.UserUnRegisterForm;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUnRegisterServiceTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @InjectMocks
    private UserUnRegisterService userUnRegisterService;

    @Captor
    private ArgumentCaptor<UserUnRegisterForm> userUnRegisterFormCaptor;

    @Test
    void 회원탈퇴_성공() {

        // Given
        String userId = "userId123";
        UserUnRegisterCommand userUnRegisterCommand = UserUnRegisterCommand.builder()
            .userId(userId)
            .password("Password1")
            .build();

        when(userPersistencePort.unRegisterUser(any(UserUnRegisterForm.class))).thenReturn(true);

        // When
        boolean result = userUnRegisterService.unRegister(userUnRegisterCommand);

        // Then
        assertThat(result).isTrue();

        verify(userPersistencePort, times(1)).unRegisterUser(
            userUnRegisterFormCaptor.capture());

        UserUnRegisterForm capturedForm = userUnRegisterFormCaptor.getValue();

        assertThat(capturedForm.getUserId()).isEqualTo(userId);
        assertThat(capturedForm.getStatus()).isEqualTo(UserStatus.UNREGISTERED);
        assertThat(capturedForm.getUnRegisterAt()).isNotNull();

    }

}

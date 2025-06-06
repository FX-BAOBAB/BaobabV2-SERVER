package user.application;

import global.enums.DeviceType;
import global.enums.UserRole;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.adapter.input.web.request.UserLoginRequest;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.application.port.output.UserPersistencePort;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.user.PasswordMismatchException;
import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;
import user.domain.dto.UserAccount;
import user.security.jwt.service.TokenIssueService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLoginServiceTest {

    @Mock
    private TokenIssueService tokenIssueService;

    @Mock
    private UserPersistencePort userPersistencePort;

    @InjectMocks
    private UserLoginService userLoginService;

    @Test
    void 로그인_성공() {

        // Given
        String email = "baobab12@baobab.com";
        String password = "Password1";
        String encryptPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserLoginCommand userLoginCommand = UserLoginCommand.builder()
            .email(email)
            .password(password)
            .build();

        UserDocument userDocument = UserDocument.builder()
            .id("login-user-id")
            .role(UserRole.BASIC_USER)
            .userAccount(UserAccount.builder()
                .email(email)
                .password(encryptPassword)
                .build())
            .build();

        TokenCommand tokenCommand = TokenCommand.builder()
            .accessToken("accessToken")
            .refreshToken("refreshToken")
            .build();

        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password, DeviceType.WEB);

        when(userPersistencePort.getUserDocumentBy(userLoginCommand.getEmail(),
            UserStatus.REGISTERED)).thenReturn(userDocument);

        when(tokenIssueService.issueToken(userDocument, DeviceType.WEB)).thenReturn(tokenCommand);


        // When
        TokenCommand result = userLoginService.login(UserLoginCommand.of(userLoginRequest));


        // Then
        verify(userPersistencePort, times(1)).setLastLoginAt(eq("login-user-id"), any(LocalDateTime.class));
        verify(tokenIssueService, times(1)).issueToken(userDocument, DeviceType.WEB);


        assertThat(result.getAccessToken()).isEqualTo("accessToken");
        assertThat(result.getRefreshToken()).isEqualTo("refreshToken");

    }

    @Test
    void 로그인_실패_잘못된_비밀번호() {

        // Given
        String email = "baobab12@baobab.com";
        String password = "WrongPassword";
        String encryptPassword = BCrypt.hashpw("Password1", BCrypt.gensalt());

        UserLoginCommand userLoginCommand = UserLoginCommand.builder()
            .email(email)
            .password(password)
            .build();

        UserDocument userDocument = UserDocument.builder()
            .id("login-user-id")
            .role(UserRole.BASIC_USER)
            .userAccount(UserAccount.builder()
                .email(email)
                .password(encryptPassword)
                .build())
            .build();

        when(userPersistencePort.getUserDocumentBy(email, UserStatus.REGISTERED)).thenReturn(userDocument);

        // When & Then
        assertThatThrownBy(() -> userLoginService.login(userLoginCommand))
            .isInstanceOf(PasswordMismatchException.class)
            .hasMessageContaining(UserErrorCode.PASSWORD_MISMATCH.getDescription());

        verify(userPersistencePort, never()).setLastLoginAt(any(), any());
        verify(tokenIssueService, never()).issueToken(any(), any());
    }

}
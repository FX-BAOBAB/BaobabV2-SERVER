package user.application;

import global.user.UserRole;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.output.RedisCachePort;
import user.application.port.output.UserPersistencePort;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.user.EmailVerificationCodeMismatchException;
import user.core.common.exception.user.EmailVerificationExpiredException;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;
import user.domain.dto.VerificationPayload;
import user.domain.form.EmailVerificationForm;
import user.domain.form.UserRegisterForm;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {

    @Mock
    private UserPersistencePort userPersistencePort;
    @Mock
    private RedisCachePort redisCachePort;
    @Mock
    private MailService mailService;

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @Test
    void 메일_검증_성공() {

        // Given
        String email = "baobab12@baobab.com";
        String verificationCode = "12345";

        UserRegisterForm registerForm = mock(UserRegisterForm.class);
        VerificationPayload verificationPayload = VerificationPayload.of(verificationCode,
            registerForm);

        EmailVerificationForm emailVerificationForm = new EmailVerificationForm(email,
            verificationCode);

        when(redisCachePort.getVerificationData("email:verification:" + email)).thenReturn(
            Optional.of(verificationPayload));

        // When
        Boolean result = emailVerificationService.verifyEmail(emailVerificationForm);

        // Then
        assertThat(result).isTrue();
        verify(userPersistencePort, times(1)).saveUser(verificationPayload.getUserInfo());
        verify(redisCachePort, times(1)).delete("email:verification:" + email);
    }

    @Test
    void 메일_검증_만료_실패() {

        // Given
        String email = "baobab12@baobab.com";
        when(redisCachePort.getVerificationData("email:verification:" + email)).thenReturn(
            Optional.empty());
        EmailVerificationForm emailVerificationForm = new EmailVerificationForm(email,
            "12345");

        // When & Then
        assertThatThrownBy(() -> emailVerificationService.verifyEmail(emailVerificationForm))
            .isInstanceOf(EmailVerificationExpiredException.class)
            .hasMessageContaining(UserErrorCode.EMAIL_VERIFICATION_EXPIRED.getDescription());
    }

    @Test
    void 메일_검증_인증코드_불일치_실패() {

        // Given
        String email = "baobab12@baobab.com";
        UserRegisterForm registerForm = mock(UserRegisterForm.class);
        VerificationPayload payload = VerificationPayload.of("12345", registerForm);

        when(redisCachePort.getVerificationData("email:verification:" + email)).thenReturn(
            Optional.of(payload));

        EmailVerificationForm form = new EmailVerificationForm(email, "54321");

        // When & Then
        assertThatThrownBy(() -> emailVerificationService.verifyEmail(form))
            .isInstanceOf(EmailVerificationCodeMismatchException.class)
            .hasMessageContaining(UserErrorCode.EMAIL_VERIFICATION_CODE_MISMATCH.getDescription());
    }

    @Test
    void 인증코드_전송_성공() {
        // Given
        UserRegisterForm userRegisterForm = UserRegisterForm.builder()
            .userAccount(UserAccount.builder()
                .email("baobab12@baobab.com")
                .password("Password1")
                .name("오밥이")
                .build())
            .nickName("오밥이")
            .userPhoneInfo(UserPhoneInfo.builder()
                .carrierType(CarrierType.KT)
                .phoneNumber("010-1234-5678")
                .build())
            .genderType(GenderType.MALE)
            .isForeigner(false)
            .birth(LocalDate.of(2001, 9, 7))
            .userAddress(UserAddress.builder()
                .address("Seoul")
                .detailAddress("Gangnam")
                .basicAddress(true)
                .post("12345")
                .build())
            .role(UserRole.BASIC_USER)
            .status(UserStatus.REGISTERED)
            .registeredAt(LocalDateTime.now())
            .build();

        // When
        emailVerificationService.sendVerification(userRegisterForm);

        // Then
        verify(redisCachePort, times(1)).save(
            startsWith("email:verification:"),
            any(VerificationPayload.class),
            eq(5L)
        );
        verify(mailService, times(1)).sendVerificationMail(eq("baobab12@baobab.com"), anyString());

    }

}
package user.application;

import global.user.UserRole;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.email.EmailVerificationService;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;
import user.domain.form.UserRegisterForm;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest  {

    @Mock
    private EmailVerificationService emailVerificationService;

    @InjectMocks
    private UserRegisterService userRegisterService;

    @Test
    void 회원가입_성공() {
        UserRegisterCommand userRegisterCommand = UserRegisterCommand.builder()
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

        doNothing().when(emailVerificationService).sendVerification(any(UserRegisterForm.class));

        // when
        Boolean result = userRegisterService.register(userRegisterCommand);

        // then
        verify(emailVerificationService, times(1)).sendVerification(any(UserRegisterForm.class));
        assertThat(result).isTrue();

    }

}
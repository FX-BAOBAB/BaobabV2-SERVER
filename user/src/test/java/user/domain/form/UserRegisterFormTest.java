package user.domain.form;

import static org.assertj.core.api.Assertions.*;

import global.user.UserRole;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;

class UserRegisterFormTest {

    @Test
    void userRegisterForm_변환_테스트() {
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

        // When
        UserRegisterForm userRegisterForm = UserRegisterForm.of(userRegisterCommand,
            "encryptedPassword");

        assertThat(userRegisterForm.getUserAccount().getEmail()).isEqualTo("baobab12@baobab.com");
        assertThat(userRegisterForm.getUserAccount().getPassword()).isEqualTo("encryptedPassword");
        assertThat(userRegisterForm.getNickName()).isEqualTo("오밥이");
        assertThat(userRegisterForm.getUserPhoneInfo().getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(userRegisterForm.getGenderType()).isEqualTo(GenderType.MALE);
        assertThat(userRegisterForm.getBirth()).isEqualTo(LocalDate.of(2001, 9, 7));
        assertThat(userRegisterForm.getUserAddress().getAddress()).isEqualTo("Seoul");
        assertThat(userRegisterForm.getUserAddress().getDetailAddress()).isEqualTo("Gangnam");
        assertThat(userRegisterForm.getUserAddress().getBasicAddress()).isTrue();
        assertThat(userRegisterForm.getUserAddress().getPost()).isEqualTo("12345");
        assertThat(userRegisterForm.getRole()).isEqualTo(UserRole.BASIC_USER);
        assertThat(userRegisterForm.getStatus()).isEqualTo(UserStatus.REGISTERED);
        assertThat(userRegisterForm.getRegisteredAt()).isNotNull();

    }

}

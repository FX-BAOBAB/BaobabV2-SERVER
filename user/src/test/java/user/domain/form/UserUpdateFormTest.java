package user.domain.form;

import org.junit.jupiter.api.Test;
import user.domain.command.UserUpdateCommand;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;
import user.adapter.output.persistence.repository.UserDocument;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserStatus;
import global.user.UserRole;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

class UserUpdateFormTest {

    @Test
    void userUpdateForm_변환_테스트() {

        // Given
        UserUpdateCommand userUpdateCommand = UserUpdateCommand.builder()
            .userId("user123")
            .nickName("오밥이")
            .userPhoneInfo(UserPhoneInfo.builder()
                .carrierType(CarrierType.KT)
                .phoneNumber("010-1234-5678")
                .build())
            .userAddress(UserAddress.builder()
                .address("Seoul")
                .detailAddress("Gangnam")
                .basicAddress(true)
                .post("12345")
                .build())
            .profileImage(null)
            .deleteImageId(null)
            .build();

        UserDocument userDocument = UserDocument.builder()
            .id("user123")
            .userAccount(UserAccount.builder()
                .email("baobab12@baobab.com")
                .password("encryptedPassword")
                .name("오밥이")
                .build())
            .nickName("기존 닉네임")
            .userPhoneInfo(UserPhoneInfo.builder()
                .carrierType(CarrierType.SKT)
                .phoneNumber("010-8765-4321")
                .build())
            .genderType(GenderType.MALE)
            .isForeigner(false)
            .birth(LocalDate.of(2000, 1, 1))
            .profileImage(null)
            .userAddress(UserAddress.builder()
                .address("서울시")
                .detailAddress("강남구")
                .basicAddress(true)
                .post("54321")
                .build())
            .role(UserRole.BASIC_USER)
            .status(UserStatus.REGISTERED)
            .registeredAt(LocalDateTime.now())
            .unRegisteredAt(null)
            .lastLoginAt(LocalDateTime.now())
            .build();

        // When
        UserUpdateForm userUpdateForm = UserUpdateForm.toForm(userUpdateCommand, userDocument);

        // Then
        assertThat(userUpdateForm.getUserId()).isEqualTo("user123");
        assertThat(userUpdateForm.getNickName()).isEqualTo("오밥이");
        assertThat(userUpdateForm.getUserPhoneInfo().getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(userUpdateForm.getUserAddress().getAddress()).isEqualTo("Seoul");
        assertThat(userUpdateForm.getUserAddress().getDetailAddress()).isEqualTo("Gangnam");
        assertThat(userUpdateForm.getUserAddress().getPost()).isEqualTo("12345");
        assertThat(userUpdateForm.getRole()).isEqualTo(UserRole.BASIC_USER);
        assertThat(userUpdateForm.getStatus()).isEqualTo(UserStatus.REGISTERED);
        assertThat(userUpdateForm.getRegisteredAt()).isNotNull();
        assertThat(userUpdateForm.getUserAccount().getEmail()).isEqualTo("baobab12@baobab.com");
        assertThat(userUpdateForm.getUserAccount().getName()).isEqualTo("오밥이");
        assertThat(userUpdateForm.getUserPhoneInfo().getCarrierType()).isEqualTo(CarrierType.KT);
        assertThat(userUpdateForm.getBirth()).isEqualTo(LocalDate.of(2000, 1, 1));
    }

}

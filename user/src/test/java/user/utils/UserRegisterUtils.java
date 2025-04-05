package user.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.GenderType;
import global.user.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.UserRegisterService;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;

@Component
public class UserRegisterUtils {

    @Autowired
    private UserRegisterService userRegisterService;

    public String registerUser(String email, String password) {

        UserRegisterCommand userRegisterCommand = UserRegisterCommand.builder()
            .userAccount(UserAccount.builder()
                .email(email)
                .password(password)
                .name("Test User")
                .build())
            .nickName("testUser")
            .userPhoneInfo(UserPhoneInfo.builder()
                .carrierType(CarrierType.KT)
                .phoneNumber("010-0000-0000")
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

        return userRegisterService.register(userRegisterCommand);
    }

}

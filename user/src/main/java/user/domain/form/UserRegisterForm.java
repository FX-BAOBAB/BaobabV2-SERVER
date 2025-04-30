package user.domain.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.GenderType;
import global.user.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.UserPhoneInfo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterForm {

    private UserAccount userAccount;

    private String nickName;

    private UserPhoneInfo userPhoneInfo;

    private GenderType genderType;

    private Boolean isForeigner;

    private LocalDate birth;

    private UserAddress userAddress;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    public static UserRegisterForm of(
        UserRegisterCommand userRegisterCommand, String encryptedPassword
    ) {
        return UserRegisterForm.builder()
            .userAccount(UserAccount.builder()
                .email(userRegisterCommand.getUserAccount().getEmail())
                .password(encryptedPassword)
                .name(userRegisterCommand.getUserAccount().getName())
                .build()
            )
            .nickName(userRegisterCommand.getNickName())
            .userPhoneInfo(userRegisterCommand.getUserPhoneInfo())
            .genderType(userRegisterCommand.getGenderType())
            .isForeigner(userRegisterCommand.getIsForeigner())
            .birth(userRegisterCommand.getBirth())
            .userAddress(userRegisterCommand.getUserAddress())
            .role(UserRole.BASIC_USER)
            .status(UserStatus.REGISTERED)
            .registeredAt(LocalDateTime.now())
            .build();
    }

}

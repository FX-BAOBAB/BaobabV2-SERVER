package user.domain.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import user.adapter.output.persistence.enums.GenderType;
import global.user.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.command.UserUpdateCommand;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserPhoneInfo;

@Data
@Builder
public class UserUpdateForm {

    private String userId;

    private UserAccount userAccount;

    private String nickName;

    private UserPhoneInfo userPhoneInfo;

    private LocalDate birth;

    private GenderType genderType;

    private Boolean isForeigner;

    private ProfileImage profileImage;

    private UserAddress userAddress;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime unRegisteredAt;

    private LocalDateTime lastLoginAt;

    public static UserUpdateForm toForm(UserUpdateCommand userUpdateCommand, UserDocument userDocument) {
        return UserUpdateForm.builder()
            .userId(userUpdateCommand.getUserId())
            .userAccount(userDocument.getUserAccount())
            .nickName(userUpdateCommand.getNickName()) // 변경되는 정보
            .userPhoneInfo(userUpdateCommand.getUserPhoneInfo()) // 변경되는 정보
            .birth(userDocument.getBirth())
            .genderType(userDocument.getGenderType())
            .isForeigner(userDocument.getIsForeigner())
            .userAddress(userUpdateCommand.getUserAddress()) // 변경되는 필드
            .role(userDocument.getRole())
            .status(userDocument.getStatus())
            .registeredAt(userDocument.getRegisteredAt())
            .unRegisteredAt(userDocument.getUnRegisteredAt())
            .lastLoginAt(userDocument.getLastLoginAt())
            .profileImage(userDocument.getProfileImage())
            .build();
    }

}

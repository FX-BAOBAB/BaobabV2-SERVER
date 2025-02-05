package user.domain.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;

@Data
@Builder
@AllArgsConstructor
public class UserReaderCommand {

    private String userId;

    private String email;

    private String nickName;

    private String name;

    private UserPhoneInfo userPhoneInfo;

    private GenderType genderType;

    private Boolean isForeigner;

    private LocalDate birth;

    private UserAddress userAddress;

    private ProfileImage profileImage;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime unRegisteredAt;

    private LocalDateTime lastLoginAt;

    public static UserReaderCommand of(UserDocument userDocument) {
        return UserReaderCommand.builder()
            .userId(userDocument.getId())
            .email(userDocument.getUserAccount().getEmail())
            .nickName(userDocument.getNickName())
            .name(userDocument.getUserAccount().getName())
            .userPhoneInfo(userDocument.getUserPhoneInfo())
            .genderType(userDocument.getGenderType())
            .isForeigner(userDocument.getIsForeigner())
            .birth(userDocument.getBirth())
            .userAddress(userDocument.getUserAddress())
            .profileImage(userDocument.getProfileImage())
            .role(userDocument.getRole())
            .status(userDocument.getStatus())
            .registeredAt(userDocument.getRegisteredAt())
            .unRegisteredAt(userDocument.getUnRegisteredAt())
            .lastLoginAt(userDocument.getLastLoginAt())
            .build();
    }

}
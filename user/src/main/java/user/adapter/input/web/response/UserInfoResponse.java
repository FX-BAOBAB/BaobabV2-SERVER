package user.adapter.input.web.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.command.UserReaderCommand;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

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

    public static UserInfoResponse toResponse(UserReaderCommand userReaderCommand) {
        return UserInfoResponse.builder()
            .userId(userReaderCommand.getUserId())
            .email(userReaderCommand.getEmail())
            .nickName(userReaderCommand.getNickName())
            .name(userReaderCommand.getName())
            .userPhoneInfo(userReaderCommand.getUserPhoneInfo())
            .genderType(userReaderCommand.getGenderType())
            .isForeigner(userReaderCommand.getIsForeigner())
            .birth(userReaderCommand.getBirth())
            .userAddress(userReaderCommand.getUserAddress())
            .profileImage(userReaderCommand.getProfileImage())
            .role(userReaderCommand.getRole())
            .status(userReaderCommand.getStatus())
            .registeredAt(userReaderCommand.getRegisteredAt())
            .unRegisteredAt(userReaderCommand.getUnRegisteredAt())
            .lastLoginAt(userReaderCommand.getLastLoginAt())
            .build();
    }

}

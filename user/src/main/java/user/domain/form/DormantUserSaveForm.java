package user.domain.form;

import global.user.UserRole;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DormantUserSaveForm {

    private String id;

    private String nickName;

    private UserPhoneInfo userPhoneInfo;

    private GenderType genderType;

    private Boolean isForeigner;

    private LocalDate birth;

    private ProfileImage profileImage;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime unRegisteredAt;

    private LocalDateTime lastLoginAt;

    private UserAccount userAccount;

    private UserAddress userAddress;

    public static DormantUserSaveForm of(UserDocument userDocument) {
        return DormantUserSaveForm.builder()
            .id(userDocument.getId())
            .nickName(userDocument.getNickName())
            .userPhoneInfo(UserPhoneInfo.builder()
                .carrierType(userDocument.getUserPhoneInfo().getCarrierType())
                .phoneNumber(userDocument.getUserPhoneInfo().getPhoneNumber())
                .build()
            )
            .genderType(userDocument.getGenderType())
            .isForeigner(userDocument.getIsForeigner())
            .birth(userDocument.getBirth())
            .profileImage(
                userDocument.getProfileImage() != null
                    ? ProfileImage.builder()
                    .imageId(userDocument.getProfileImage().getImageId())
                    .imageUrl(userDocument.getProfileImage().getImageUrl())
                    .build()
                    : null
            )
            .role(userDocument.getRole())
            .status(userDocument.getStatus())
            .registeredAt(userDocument.getRegisteredAt())
            .unRegisteredAt(userDocument.getUnRegisteredAt())
            .lastLoginAt(userDocument.getLastLoginAt())
            .userAccount(UserAccount.builder()
                .email(userDocument.getUserAccount().getEmail())
                .password(userDocument.getUserAccount().getPassword())
                .build()
            )
            .userAddress(UserAddress.builder()
                .address(userDocument.getUserAddress().getAddress())
                .detailAddress(userDocument.getUserAddress().getDetailAddress())
                .basicAddress(userDocument.getUserAddress().getBasicAddress())
                .post(userDocument.getUserAddress().getPost())
                .build()
            )
            .build();
    }

}

package user.adapter.output.persistence.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.GenderType;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserPhoneInfo;
import user.domain.form.UserRegisterForm;
import user.domain.form.UserUpdateForm;

@Data
@Builder
@Document(collection = "user")
public class UserDocument {

    @Id
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

    public static UserDocument of(UserUpdateForm userUpdateForm) {
        return UserDocument.builder()
            .id(userUpdateForm.getUserId())
            .userAccount(userUpdateForm.getUserAccount())
            .nickName(userUpdateForm.getNickName())
            .userPhoneInfo(userUpdateForm.getUserPhoneInfo())
            .genderType(userUpdateForm.getGenderType())
            .isForeigner(userUpdateForm.getIsForeigner())
            .birth(userUpdateForm.getBirth())
            .profileImage(userUpdateForm.getProfileImage())
            .userAddress(userUpdateForm.getUserAddress())
            .role(userUpdateForm.getRole())
            .status(userUpdateForm.getStatus())
            .registeredAt(userUpdateForm.getRegisteredAt())
            .unRegisteredAt(userUpdateForm.getUnRegisteredAt())
            .lastLoginAt(userUpdateForm.getLastLoginAt())
            .build();
    }

    public static UserDocument of(UserRegisterForm userRegisterForm) {
        return UserDocument.builder()
            .userAccount(userRegisterForm.getUserAccount())
            .nickName(userRegisterForm.getNickName())
            .userPhoneInfo(userRegisterForm.getUserPhoneInfo())
            .genderType(userRegisterForm.getGenderType())
            .isForeigner(userRegisterForm.getIsForeigner())
            .birth(userRegisterForm.getBirth())
            .role(userRegisterForm.getRole())
            .status(userRegisterForm.getStatus())
            .registeredAt(userRegisterForm.getRegisteredAt())
            .userAddress(userRegisterForm.getUserAddress())
            .build();
    }

}

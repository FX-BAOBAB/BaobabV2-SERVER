package user.adapter.output.persistence.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserRegisterForm;
import user.domain.dto.UserUpdateForm;

@Data
@Builder
@Document(collection = "user")
public class UserDocument {

    @Id
    private String id;

    private String nickName;

    private String phone;

    private LocalDate birth;

    private ProfileImage profileImage;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime unRegisteredAt;

    private LocalDateTime lastLoginAt;

    private Account account;

    private Address address;

    public static UserDocument of(UserUpdateForm userUpdateForm) {
        return UserDocument.builder()
            .id(userUpdateForm.getUserId())
            .nickName(userUpdateForm.getNickName())
            .phone(userUpdateForm.getPhone())
            .birth(userUpdateForm.getBirth())
            .profileImage(userUpdateForm.getProfileImage())
            .role(userUpdateForm.getRole())
            .status(UserStatus.REGISTERED)
            .registeredAt(userUpdateForm.getRegisteredAt())
            .unRegisteredAt(userUpdateForm.getUnRegisteredAt())
            .lastLoginAt(userUpdateForm.getLastLoginAt())
            .account(
                Account.builder()
                    .email(userUpdateForm.getEmail())
                    .password(userUpdateForm.getPassword())
                    .name(userUpdateForm.getName())
                    .build()
            )
            .address(
                Address.builder()
                    .address(userUpdateForm.getAddress())
                    .basicAddress(userUpdateForm.getBasicAddress())
                    .detailAddress(userUpdateForm.getDetailAddress())
                    .post(userUpdateForm.getPost())
                    .build()
            )
            .build();
    }

    public static UserDocument of(UserRegisterForm userRegisterForm) {
        return UserDocument.builder()
            .nickName(userRegisterForm.getNickName())
            .phone(userRegisterForm.getPhone())
            .birth(userRegisterForm.getBirth())
            .role(userRegisterForm.getRole())
            .status(userRegisterForm.getStatus())
            .registeredAt(userRegisterForm.getRegisteredAt())
            .account(Account.builder()
                .email(userRegisterForm.getEmail())
                .password(userRegisterForm.getEncodingPassword())
                .name(userRegisterForm.getName())
                .build())
            .address(Address.builder()
                .address(userRegisterForm.getAddress())
                .detailAddress(userRegisterForm.getDetailAddress())
                .basicAddress(userRegisterForm.getBasicAddress())
                .post(userRegisterForm.getPost())
                .build())
            .build();
    }

}

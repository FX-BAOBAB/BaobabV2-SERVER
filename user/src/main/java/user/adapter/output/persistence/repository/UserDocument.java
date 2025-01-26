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
import user.domain.dto.UserUpdateForm;

@Data
@Builder
@Document(collection = "user")
public class UserDocument {

    @Id
    private String id;

    private String nickName;

    private String phone;

    private String department;

    private LocalDate birth;

    private ProfileImage profileImage;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime unRegisteredAt;

    private LocalDateTime lastLoginAt;

    private Account account;

    private Address address;

    public static UserDocument toUserDocument(UserUpdateForm userUpdateForm, UserDocument userDocument) {
        return UserDocument.builder()
            .id(userDocument.getId())
            .nickName(userUpdateForm.getNickName())
            .phone(userUpdateForm.getPhone())
            .department(userUpdateForm.getDepartment())
            .birth(userUpdateForm.getBirth())
            .profileImage(userUpdateForm.getProfileImage())
            .role(userDocument.getRole())
            .status(UserStatus.REGISTERED)
            .registeredAt(userDocument.getRegisteredAt())
            .unRegisteredAt(userDocument.getUnRegisteredAt())
            .lastLoginAt(userDocument.getLastLoginAt())
            .account(
                Account.builder()
                    .email(userDocument.getAccount().getEmail())
                    .password(userDocument.getAccount().getPassword())
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

}

package user.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.command.UserUpdateCommand;

@Data
@Builder
public class UserUpdateForm {

    private String userId;

    private String email;

    private String password;

    private String name;

    private String nickName;

    private String phone;

    private String department;

    private LocalDate birth;

    private ProfileImage profileImage;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime unRegisteredAt;

    private LocalDateTime lastLoginAt;

    public static UserUpdateForm toForm(UserUpdateCommand userUpdateCommand, UserDocument userDocument) {
        return UserUpdateForm.builder()
            .userId(userUpdateCommand.getUserId())
            .email(userDocument.getAccount().getEmail())
            .password(userDocument.getAccount().getPassword())
            .nickName(userUpdateCommand.getNickName())
            .name(userUpdateCommand.getName())
            .phone(userUpdateCommand.getPhone())
            .birth(userUpdateCommand.getBirth())
            .department(userUpdateCommand.getDepartment())
            .address(userUpdateCommand.getAddress())
            .detailAddress(userUpdateCommand.getDetailAddress())
            .basicAddress(userUpdateCommand.getBasicAddress())
            .post(userUpdateCommand.getPost())
            .role(userDocument.getRole())
            .status(userDocument.getStatus())
            .registeredAt(userDocument.getRegisteredAt())
            .unRegisteredAt(userDocument.getUnRegisteredAt())
            .lastLoginAt(userDocument.getLastLoginAt())
            .profileImage(userDocument.getProfileImage())
            .build();
    }

}

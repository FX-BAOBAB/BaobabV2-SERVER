package user.domain.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.dto.ProfileImage;

@Data
@Builder
public class UserReaderCommand {

    private String userId;

    private String email;

    private String nickName;

    private String name;

    private String phone;

    private String department;

    private LocalDate birth;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private ProfileImage profileImage;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime unRegisteredAt;

    private LocalDateTime lastLoginAt;

    public static UserReaderCommand of(UserDocument userDocument) {
        return UserReaderCommand.builder()
            .userId(userDocument.getId())
            .email(userDocument.getAccount().getEmail())
            .nickName(userDocument.getNickName())
            .name(userDocument.getAccount().getName())
            .phone(userDocument.getPhone())
            .department(userDocument.getDepartment())
            .birth(userDocument.getBirth())
            .address(userDocument.getAddress().getAddress())
            .detailAddress(userDocument.getAddress().getDetailAddress())
            .basicAddress(userDocument.getAddress().getBasicAddress())
            .post(userDocument.getAddress().getPost())
            .profileImage(userDocument.getProfileImage())
            .role(userDocument.getRole())
            .status(userDocument.getStatus())
            .registeredAt(userDocument.getRegisteredAt())
            .unRegisteredAt(userDocument.getUnRegisteredAt())
            .lastLoginAt(userDocument.getLastLoginAt())
            .build();
    }

}
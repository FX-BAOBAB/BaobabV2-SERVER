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

    public static UserReaderCommand toCommand(UserDocument user) {
        return UserReaderCommand.builder()
            .userId(user.getId())
            .email(user.getAccount().getEmail())
            .nickName(user.getNickName())
            .name(user.getAccount().getName())
            .phone(user.getPhone())
            .department(user.getDepartment())
            .birth(user.getBirth())
            .address(user.getAddress().getAddress())
            .detailAddress(user.getAddress().getDetailAddress())
            .basicAddress(user.getAddress().getBasicAddress())
            .post(user.getAddress().getPost())
            .profileImage(user.getProfileImage())
            .role(user.getRole())
            .status(user.getStatus())
            .registeredAt(user.getRegisteredAt())
            .unRegisteredAt(user.getUnRegisteredAt())
            .lastLoginAt(user.getLastLoginAt())
            .build();
    }

}
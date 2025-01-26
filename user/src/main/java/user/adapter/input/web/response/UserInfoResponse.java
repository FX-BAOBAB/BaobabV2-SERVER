package user.adapter.input.web.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.command.UserReaderCommand;
import user.domain.dto.ProfileImage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
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

    public static UserInfoResponse toResponse(UserReaderCommand userReaderCommand) {
        return UserInfoResponse.builder()
            .userId(userReaderCommand.getUserId())
            .email(userReaderCommand.getEmail())
            .nickName(userReaderCommand.getNickName())
            .name(userReaderCommand.getName())
            .phone(userReaderCommand.getPhone())
            .department(userReaderCommand.getDepartment())
            .birth(userReaderCommand.getBirth())
            .address(userReaderCommand.getAddress())
            .detailAddress(userReaderCommand.getDetailAddress())
            .basicAddress(userReaderCommand.getBasicAddress())
            .post(userReaderCommand.getPost())
            .profileImage(userReaderCommand.getProfileImage())
            .role(userReaderCommand.getRole())
            .status(userReaderCommand.getStatus())
            .registeredAt(userReaderCommand.getRegisteredAt())
            .unRegisteredAt(userReaderCommand.getUnRegisteredAt())
            .lastLoginAt(userReaderCommand.getLastLoginAt())
            .build();
    }

}

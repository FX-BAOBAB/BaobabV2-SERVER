package user.domain.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;

@Data
@Builder
public class UserRegisterCommand {

    private String email;

    private String password;

    private String nickName;

    private String name;

    private String phone;

    private LocalDate birth;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    public static UserRegisterCommand of(UserRegisterRequest userRegisterRequest) {
        return UserRegisterCommand.builder()
            .email(userRegisterRequest.getEmail())
            .password(userRegisterRequest.getPassword())
            .nickName(userRegisterRequest.getNickName())
            .name(userRegisterRequest.getNickName())
            .phone(userRegisterRequest.getPhone())
            .birth(userRegisterRequest.getBirth())
            .address(userRegisterRequest.getAddress())
            .detailAddress(userRegisterRequest.getDetailAddress())
            .basicAddress(userRegisterRequest.getBasicAddress())
            .post(userRegisterRequest.getPost())
            .build();
    }

}
package user.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.command.UserRegisterCommand;

@Data
@Builder
public class UserRegisterForm {

    private String email;

    private String encodingPassword;

    private String nickName;

    private String name;

    private String phone;

    private String department;

    private LocalDate birth;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    public static UserRegisterForm toForm(UserRegisterCommand userRegisterCommand) {
        return UserRegisterForm.builder()
            .email(userRegisterCommand.getEmail())
            .encodingPassword(
                BCrypt.hashpw(userRegisterCommand.getPassword(), BCrypt.gensalt())
            )
            .nickName(userRegisterCommand.getNickName())
            .name(userRegisterCommand.getName())
            .phone(userRegisterCommand.getPhone())
            .department(userRegisterCommand.getDepartment())
            .birth(userRegisterCommand.getBirth())
            .address(userRegisterCommand.getAddress())
            .detailAddress(userRegisterCommand.getDetailAddress())
            .basicAddress(userRegisterCommand.getBasicAddress())
            .post(userRegisterCommand.getPost())
            .role(UserRole.BASIC_USER)
            .status(UserStatus.REGISTERED)
            .registeredAt(LocalDateTime.now())
            .build();
    }

}

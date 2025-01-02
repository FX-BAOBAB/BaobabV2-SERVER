package user.domain.command;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterCommand {

    private String email;

    private String password;

    private String name;

    private String nickName;

    private String department;

    private LocalDate birth;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private Long imageId;

}
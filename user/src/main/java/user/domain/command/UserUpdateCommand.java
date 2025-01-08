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
public class UserUpdateCommand {

    private String userId;

    private String password;

    private String nickName;

    private String name;

    private String phone;

    private LocalDate birth;

    private String department;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private String imageId;

}

package user.adapter.input.web.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    // TODO 유효성 검사
    private String email;

    private String password;

    private String nickName;

    private String name;

    private String department;

    private LocalDate birth;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private String imageId;

}

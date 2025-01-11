package user.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateForm {

    private String userId;

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

}

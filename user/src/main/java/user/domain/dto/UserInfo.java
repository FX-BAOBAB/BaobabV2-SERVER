package user.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.repository.Address;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String userId;

    private String userName;

    private String nickName;

    private String phone;

    private String department;

    private LocalDate birth;

    private UserRole userRole;

    private ProfileImage profileImage;

    private Address address;

}

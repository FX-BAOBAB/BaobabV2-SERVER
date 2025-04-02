package user.security.jwt.model;

import global.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenInfoDto {

    private String userId;

    private UserRole userRole;

}

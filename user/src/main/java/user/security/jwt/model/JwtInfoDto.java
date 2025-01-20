package user.security.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.UserRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtInfoDto {

    private String userId;

    private String nickName;

    private UserRole role;

}

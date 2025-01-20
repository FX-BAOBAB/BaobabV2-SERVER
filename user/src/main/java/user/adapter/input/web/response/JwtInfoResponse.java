package user.adapter.input.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.UserRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtInfoResponse {

    private String userId;

    private String nickName;

    private UserRole role;

}

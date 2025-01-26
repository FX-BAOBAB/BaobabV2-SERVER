package user.adapter.input.web.response;

import lombok.Builder;
import lombok.Data;
import user.adapter.output.persistence.enums.UserRole;
import user.security.jwt.model.JwtInfoDto;

@Data
@Builder
public class JwtInfoResponse {

    private String userId;

    private String nickName;

    private UserRole role;

    public static JwtInfoResponse toResponse(JwtInfoDto jwtInfoDto) {
        return JwtInfoResponse.builder()
            .userId(jwtInfoDto.getUserId())
            .nickName(jwtInfoDto.getNickName())
            .role(jwtInfoDto.getRole())
            .build();
    }

}

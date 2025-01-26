package user.security.jwt.model;

import lombok.Builder;
import lombok.Data;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.repository.UserDocument;

@Data
@Builder
public class JwtInfoDto {

    private String userId;

    private String nickName;

    private UserRole role;

    public static JwtInfoDto toTokenInfo(UserDocument userDocument) {
        return JwtInfoDto.builder()
            .userId(userDocument.getId())
            .nickName(userDocument.getNickName())
            .role(userDocument.getRole())
            .build();
    }

}

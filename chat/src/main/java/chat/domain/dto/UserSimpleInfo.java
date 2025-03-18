package chat.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSimpleInfo {

    private String nickname;

    private String profileImageUrl;

}

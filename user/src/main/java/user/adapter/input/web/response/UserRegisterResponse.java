package user.adapter.input.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterResponse {

    private String userId;

    public static UserRegisterResponse toResponse(String userId) {
        return UserRegisterResponse.builder().userId(userId).build();
    }

}

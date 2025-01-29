package user.domain.command;

import lombok.Builder;
import lombok.Data;
import user.adapter.input.web.request.UserUnRegisterRequest;

@Data
@Builder
public class UserUnRegisterCommand {

    private String userId;

    private String password;

    public static UserUnRegisterCommand of(
        UserUnRegisterRequest userUnRegisterRequest, String userId
    ) {
        return UserUnRegisterCommand.builder()
            .userId(userId)
            .password(userUnRegisterRequest.getPassword())
            .build();
    }

}

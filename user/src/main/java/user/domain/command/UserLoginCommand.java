package user.domain.command;

import lombok.Builder;
import lombok.Data;
import user.adapter.input.web.request.UserLoginRequest;

@Data
@Builder
public class UserLoginCommand {

    private String email;

    private String password;

    public static UserLoginCommand toCommand(UserLoginRequest userLoginRequest) {
        return UserLoginCommand.builder()
            .email(userLoginRequest.getEmail())
            .password(userLoginRequest.getPassword())
            .build();
    }

}
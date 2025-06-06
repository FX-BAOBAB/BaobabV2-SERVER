package user.domain.command;

import global.enums.DeviceType;
import lombok.Builder;
import lombok.Data;
import user.adapter.input.web.request.UserLoginRequest;

@Data
@Builder
public class UserLoginCommand {

    private String email;

    private String password;

    private DeviceType deviceType;

    public static UserLoginCommand of(UserLoginRequest userLoginRequest) {
        return UserLoginCommand.builder()
            .email(userLoginRequest.getEmail())
            .password(userLoginRequest.getPassword())
            .deviceType(userLoginRequest.getDeviceType())
            .build();
    }

}
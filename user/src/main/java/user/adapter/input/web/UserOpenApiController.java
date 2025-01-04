package user.adapter.input.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.adapter.input.web.request.UserRegisterRequest;
import user.application.port.input.UserRegisterUseCase;
import user.core.common.annotation.DupleCheck;
import user.core.common.converter.UserConverter;
import user.domain.command.UserRegisterCommand;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserRegisterUseCase userRegisterUseCase;
    private final UserConverter userConverter;

    @PostMapping()
    @DupleCheck
    public boolean register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserRegisterCommand registerCommand = userConverter.toRegisterCommand(userRegisterRequest);
        boolean isRegistered = userRegisterUseCase.register(registerCommand);
        return isRegistered;
    }

}

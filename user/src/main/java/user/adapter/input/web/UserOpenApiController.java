package user.adapter.input.web;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.adapter.input.web.converter.UserConverter;
import user.adapter.input.web.request.UserRegisterRequest;
import user.application.port.input.UserRegisterUseCase;
import user.core.common.annotation.DupleCheck;
import user.domain.command.AccountRegisterCommand;
import user.domain.command.UserRegisterCommand;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserRegisterUseCase userRegisterUseCase;
    private final UserConverter userConverter;

    @PostMapping
    @DupleCheck
    public Api<Boolean> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserRegisterCommand command = userConverter.toCommand(userRegisterRequest);
        AccountRegisterCommand accountCommand = userConverter.toAccountCommand(userRegisterRequest);
        boolean isRegistered = userRegisterUseCase.register(command, accountCommand);
        return Api.OK(isRegistered);
    }


}

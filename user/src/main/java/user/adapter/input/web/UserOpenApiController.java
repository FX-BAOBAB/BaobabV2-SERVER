package user.adapter.input.web;

import global.api.Api;
import jakarta.validation.Valid;
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
    public Api<Boolean> register(@RequestBody @Valid Api<UserRegisterRequest> userRegisterRequest) {
        UserRegisterCommand registerCommand = userConverter.toRegisterCommand(userRegisterRequest.getBody());
        boolean isRegistered = userRegisterUseCase.register(registerCommand);
        return Api.OK(isRegistered);
    }

}

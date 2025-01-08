package user.adapter.input.web.response;

import global.annotation.input.RestAdapter;
import global.api.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import user.adapter.input.web.request.UserUpdateRequest;
import user.application.port.input.UserUpdateUseCase;
import user.core.common.converter.UserConverter;
import user.domain.command.UserUpdateCommand;

@RestAdapter
@RequiredArgsConstructor
@RequestMapping("/open-api/user") // TODO 인증/인가 구현 후 /api/user 로 수정
public class UserApiController {

    private final UserUpdateUseCase userUpdateUseCase;

    private final UserConverter userConverter;

    @PostMapping("/update")
    public Api<Boolean> update(
        @RequestBody @Valid Api<UserUpdateRequest> userUpdateRequest,
        String userId
    ) {
        UserUpdateCommand updateCommand = userConverter.toUpdateCommand(userUpdateRequest.getBody(), "677eb8d9d307a24fa71f3e8e");
        boolean isUpdated = userUpdateUseCase.updateUser(updateCommand);
        return Api.OK(isUpdated);
    }

}

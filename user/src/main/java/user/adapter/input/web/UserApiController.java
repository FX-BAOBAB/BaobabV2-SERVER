package user.adapter.input.web;

import global.annotation.AuthenticatedUser;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import user.adapter.input.web.request.UserUnRegisterRequest;
import user.adapter.input.web.request.UserUpdateRequest;
import user.adapter.input.web.response.UserInfoResponse;
import user.application.port.input.UserReaderUseCase;
import user.application.port.input.UserRegisterUseCase;
import user.application.port.input.UserUnRegisterUseCase;
import user.application.port.input.UserUpdateUseCase;
import user.core.common.annotation.DupleCheck;
import user.core.common.annotation.PasswordCheck;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserUnRegisterCommand;
import user.domain.command.UserUpdateCommand;

@RestAdapter
@RequiredArgsConstructor
public class UserApiController {

    private final UserUpdateUseCase userUpdateUseCase;
    private final UserUnRegisterUseCase userUnRegisterUseCase;
    private final UserReaderUseCase userReaderUseCase;
    private final UserRegisterUseCase userRegisterUseCase;

    @PostMapping("/update")
    @DupleCheck
    @PasswordCheck
    public Api<Boolean> update(
        @RequestPart("userUpdateRequest") @Valid Api<UserUpdateRequest> userUpdateRequest,
        @RequestPart("profileImage") MultipartFile profileImage,
        @AuthenticatedUser AuthUser authUser
    ) {
        UserUpdateCommand updateCommand = UserUpdateCommand.toCommand(
            userUpdateRequest.getBody(), profileImage, authUser.getUserId());

        boolean isUpdated = userUpdateUseCase.updateUserInfo(updateCommand);
        return Api.OK(isUpdated);
    }

    @PostMapping("/unregister")
    @PasswordCheck
    public Api<Boolean> unRegister(
        @RequestBody @Valid Api<UserUnRegisterRequest> userUnRegisterRequest,
        @AuthenticatedUser AuthUser authUser
    ) {
        UserUnRegisterCommand unRegisterCommand = UserUnRegisterCommand.toCommand(
            userUnRegisterRequest.getBody(), authUser.getUserId());

        boolean isUnRegistered = userUnRegisterUseCase.unRegister(unRegisterCommand);
        return Api.OK(isUnRegistered);
    }

    @GetMapping("/info")
    public Api<UserInfoResponse> getUserInfo(
        @AuthenticatedUser AuthUser authUser
    ) {
        UserReaderCommand userInfo = userReaderUseCase.getUserInfoBy(authUser.getUserId());
        UserInfoResponse userInfoResponse = UserInfoResponse.toResponse(userInfo);
        return Api.OK(userInfoResponse);
    }

    @PostMapping("/image")
    public Api<Boolean> registerProfileImage(
        @RequestPart("profileImage") MultipartFile profileImage,
        @AuthenticatedUser AuthUser authUser
    ) {
        Boolean isRegisteredImage = userRegisterUseCase.registerProfileImage(
            authUser.getUserId(), profileImage);
        return Api.OK(isRegisteredImage);
    }

}

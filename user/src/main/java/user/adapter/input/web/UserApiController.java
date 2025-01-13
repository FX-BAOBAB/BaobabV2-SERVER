package user.adapter.input.web;

import file.application.port.input.ImageStorageUseCase;
import file.core.common.converter.ImageConverter;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.utils.ImageIdUtils;
import jakarta.validation.Valid;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import user.adapter.input.web.request.UserUnRegisterRequest;
import user.adapter.input.web.request.UserUpdateRequest;
import user.application.port.input.UserUnRegisterUseCase;
import user.application.port.input.UserUpdateUseCase;
import user.core.common.annotation.DupleCheck;
import user.core.common.annotation.PasswordCheck;
import user.core.common.converter.UserConverter;
import user.domain.command.UserUnRegisterCommand;
import user.domain.command.UserUpdateCommand;

@RestAdapter
@RequiredArgsConstructor
@RequestMapping("/open-api/user") // TODO 인증/인가 구현 후 /api/user 로 수정
public class UserApiController {

    private final UserUpdateUseCase userUpdateUseCase;
    private final UserUnRegisterUseCase userUnRegisterUseCase;
    private final ImageStorageUseCase imageStorageUseCase;
    private final ImageIdUtils imageIdUtils;

    private final UserConverter userConverter;
    private final ImageConverter imageConverter;

    @PostMapping("/update")
    @DupleCheck
    public Api<Boolean> update(UserUpdateRequest userUpdateRequest, String userId) {
        try {
            ImageCommand imageCommand = imageConverter.toImageCommand(
                imageIdUtils.generateImageId("user", "userId"),
                userUpdateRequest.getProfileImage(), ImageKind.USER
            );

            ImageMetaData imageMetaData = imageStorageUseCase.saveImage(imageCommand).get();

            UserUpdateCommand updateCommand = userConverter.toUpdateCommand(
                userUpdateRequest, imageMetaData, userId);

            boolean isUpdated = userUpdateUseCase.updateUserInfo(updateCommand);
            return Api.OK(isUpdated);

        } catch (ExecutionException | InterruptedException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }
    }

    @PostMapping("/unregister")
    @PasswordCheck
    public Api<Boolean> unRegister(
        @RequestBody @Valid Api<UserUnRegisterRequest> userUnRegisterRequest,
        String userId
    ) {
        UserUnRegisterCommand unRegisterCommand = userConverter.toUnRegisterCommand(
            userUnRegisterRequest.getBody(), userId);

        boolean isUnRegistered = userUnRegisterUseCase.unRegister(unRegisterCommand);
        return Api.OK(isUnRegistered);
    }

}

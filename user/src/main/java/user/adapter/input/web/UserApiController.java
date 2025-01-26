package user.adapter.input.web;

import file.application.port.input.ImageStorageUseCase;
import file.core.common.converter.ImageConverter;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import global.annotation.AuthenticatedUser;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.resolver.AuthUser;
import global.utils.ImageIdUtils;
import jakarta.validation.Valid;
import java.util.concurrent.ExecutionException;
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
import user.domain.dto.ProfileImage;

@RestAdapter
@RequiredArgsConstructor
public class UserApiController {

    private final UserUpdateUseCase userUpdateUseCase;
    private final UserUnRegisterUseCase userUnRegisterUseCase;
    private final ImageStorageUseCase imageStorageUseCase;
    private final UserReaderUseCase userReaderUseCase;
    private final UserRegisterUseCase userRegisterUseCase;
    private final ImageIdUtils imageIdUtils;

    private final ImageConverter imageConverter;

    @PostMapping("/update")
    @DupleCheck @PasswordCheck
    public Api<Boolean> update(
        @RequestPart("userUpdateRequest") @Valid Api<UserUpdateRequest> userUpdateRequest,
        @RequestPart("profileImage") MultipartFile profileImage,
        @AuthenticatedUser AuthUser authUser
    ) {
        try {
            ImageCommand imageCommand = imageConverter.toImageCommand(
                imageIdUtils.generateImageId("user", authUser.getUserId()),
                profileImage, ImageKind.USER
            );

            ImageMetaData imageMetaData = imageStorageUseCase.saveImage(imageCommand).get();

            UserUpdateCommand updateCommand = UserUpdateCommand.toCommand(
                userUpdateRequest.getBody(), imageMetaData, authUser.getUserId());

            boolean isUpdated = userUpdateUseCase.updateUserInfo(updateCommand);
            return Api.OK(isUpdated);

        } catch (ExecutionException | InterruptedException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_DELETE_ERROR);
        }
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
        try {
            ImageCommand imageCommand = imageConverter.toImageCommand(
                imageIdUtils.generateImageId("user", authUser.getUserId()),
                profileImage, ImageKind.USER);

            ImageMetaData imageMetaData = imageStorageUseCase.saveImage(imageCommand).get();

            ProfileImage imageInfo = ProfileImage.builder()
                .ImageId(imageMetaData.getId())
                .ImageUrl(imageMetaData.getUrl())
                .build();

            Boolean isRegisteredImage = userRegisterUseCase.registerProfileImage(
                authUser.getUserId(), imageInfo);
            return Api.OK(isRegisteredImage);

        } catch (ExecutionException | InterruptedException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_DELETE_ERROR);
        }

    }

}

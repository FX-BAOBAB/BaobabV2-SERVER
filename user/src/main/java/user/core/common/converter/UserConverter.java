package user.core.common.converter;

import file.domain.ImageMetaData;
import global.annotation.Converter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.input.web.request.UserUnRegisterRequest;
import user.adapter.input.web.request.UserUpdateRequest;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.Account;
import user.adapter.output.persistence.repository.Address;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.command.UserUnRegisterCommand;
import user.domain.command.UserUpdateCommand;
import user.domain.dto.ProfileImage;
import user.domain.dto.UserRegisterForm;
import user.domain.dto.UserUnRegisterForm;
import user.domain.dto.UserUpdateForm;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;

    public UserRegisterCommand toRegisterCommand(UserRegisterRequest userRegisterRequest,
        ImageMetaData imageMetaData) {

        ProfileImage imageInfo = ProfileImage.builder()
            .ImageId(imageMetaData.getId())
            .ImageUrl(imageMetaData.getUrl())
            .build();

        return UserRegisterCommand.builder()
            .email(userRegisterRequest.getEmail())
            .password(userRegisterRequest.getPassword())
            .nickName(userRegisterRequest.getNickName())
            .name(userRegisterRequest.getNickName())
            .phone(userRegisterRequest.getPhone())
            .department(userRegisterRequest.getDepartment())
            .birth(userRegisterRequest.getBirth())
            .address(userRegisterRequest.getAddress())
            .detailAddress(userRegisterRequest.getDetailAddress())
            .basicAddress(userRegisterRequest.getBasicAddress())
            .post(userRegisterRequest.getPost())
            .profileImage(imageInfo)
            .build();
    }

    public UserDocument toUserDocument(UserRegisterForm userRegisterForm) {
        return UserDocument.builder()
            .nickName(userRegisterForm.getNickName())
            .phone(userRegisterForm.getPhone())
            .department(userRegisterForm.getDepartment())
            .birth(userRegisterForm.getBirth())
            .profileImage(userRegisterForm.getProfileImage())
            .role(userRegisterForm.getRole())
            .status(userRegisterForm.getStatus())
            .registeredAt(userRegisterForm.getRegisteredAt())
            .account(Account.builder()
                .email(userRegisterForm.getEmail())
                .password(userRegisterForm.getEncodingPassword())
                .name(userRegisterForm.getName())
                .build())
            .address(Address.builder()
                .address(userRegisterForm.getAddress())
                .detailAddress(userRegisterForm.getDetailAddress())
                .basicAddress(userRegisterForm.getBasicAddress())
                .post(userRegisterForm.getPost())
                .build())
            .build();
    }

    public UserReaderCommand toReaderCommand(UserDocument user) {
        return UserReaderCommand.builder()
            .userId(user.getId())
            .email(user.getAccount().getEmail())
            .password(user.getAccount().getPassword())
            .nickName(user.getNickName())
            .name(user.getAccount().getName())
            .phone(user.getPhone())
            .department(user.getDepartment())
            .birth(user.getBirth())
            .address(user.getAddress().getAddress())
            .detailAddress(user.getAddress().getDetailAddress())
            .basicAddress(user.getAddress().getBasicAddress())
            .post(user.getAddress().getPost())
            .profileImage(user.getProfileImage())
            .role(user.getRole())
            .status(user.getStatus())
            .registeredAt(user.getRegisteredAt())
            .unregisteredAt(user.getUnRegisteredAt())
            .lastLoginAt(user.getLastLoginAt())
            .build();
    }

    public UserUpdateCommand toUpdateCommand(
        UserUpdateRequest userUpdateRequest,
        ImageMetaData imageMetaData,
        String userId
    ) {
        ProfileImage imageInfo = ProfileImage.builder()
            .ImageId(imageMetaData.getId())
            .ImageUrl(imageMetaData.getUrl())
            .build();

        return UserUpdateCommand.builder()
            .userId(userId)
            .nickName(userUpdateRequest.getNickName())
            .name(userUpdateRequest.getName())
            .phone(userUpdateRequest.getPhone())
            .birth(userUpdateRequest.getBirth())
            .department(userUpdateRequest.getDepartment())
            .address(userUpdateRequest.getAddress())
            .detailAddress(userUpdateRequest.getDetailAddress())
            .basicAddress(userUpdateRequest.getBasicAddress())
            .post(userUpdateRequest.getPost())
            .profileImage(imageInfo)
            .build();
    }

    public UserRegisterForm toUserRegisterForm(UserRegisterCommand userRegisterCommand) {
        return UserRegisterForm.builder()
            .email(userRegisterCommand.getEmail())
            .encodingPassword(
                passwordEncoder.encode(userRegisterCommand.getPassword())
            )
            .nickName(userRegisterCommand.getNickName())
            .name(userRegisterCommand.getName())
            .phone(userRegisterCommand.getPhone())
            .department(userRegisterCommand.getDepartment())
            .birth(userRegisterCommand.getBirth())
            .address(userRegisterCommand.getAddress())
            .detailAddress(userRegisterCommand.getDetailAddress())
            .basicAddress(userRegisterCommand.getBasicAddress())
            .post(userRegisterCommand.getPost())
            .profileImage(userRegisterCommand.getProfileImage())
            .role(UserRole.BASIC_USER)
            .status(UserStatus.REGISTERED)
            .registeredAt(LocalDateTime.now())
            .build();
    }

    public UserUpdateForm toUpdateForm(UserUpdateCommand userUpdateCommand) {
        return UserUpdateForm.builder()
            .userId(userUpdateCommand.getUserId())
            .name(userUpdateCommand.getName())
            .phone(userUpdateCommand.getPhone())
            .birth(userUpdateCommand.getBirth())
            .department(userUpdateCommand.getDepartment())
            .address(userUpdateCommand.getAddress())
            .detailAddress(userUpdateCommand.getDetailAddress())
            .basicAddress(userUpdateCommand.getBasicAddress())
            .post(userUpdateCommand.getPost())
            .profileImage(userUpdateCommand.getProfileImage())
            .build();
    }

    public UserDocument toUserDocument(UserUpdateForm userUpdateForm, UserDocument userDocument) {
        return UserDocument.builder()
            .id(userDocument.getId())
            .nickName(userUpdateForm.getNickName())
            .phone(userUpdateForm.getPhone())
            .department(userUpdateForm.getDepartment())
            .birth(userUpdateForm.getBirth())
            .profileImage(userUpdateForm.getProfileImage())
            .role(userDocument.getRole())
            .status(UserStatus.REGISTERED)
            .registeredAt(userDocument.getRegisteredAt())
            .unRegisteredAt(userDocument.getUnRegisteredAt())
            .lastLoginAt(userDocument.getLastLoginAt())
            .account(
                Account.builder()
                    .email(userDocument.getAccount().getEmail())
                    .password(userDocument.getAccount().getPassword())
                    .name(userUpdateForm.getName())
                    .build()
            )
            .address(
                Address.builder()
                    .address(userUpdateForm.getAddress())
                    .basicAddress(userUpdateForm.getBasicAddress())
                    .detailAddress(userUpdateForm.getDetailAddress())
                    .post(userUpdateForm.getPost())
                    .build()
            )
            .build();
    }

    public UserUnRegisterCommand toUnRegisterCommand(UserUnRegisterRequest userUnRegisterRequest,
        String userId) {
        return UserUnRegisterCommand.builder()
            .userId(userId)
            .password(userUnRegisterRequest.getPassword())
            .build();
    }

    public UserUnRegisterForm toUnregisterForm(String userId) {
        return UserUnRegisterForm.builder()
            .userId(userId)
            .status(UserStatus.UNREGISTERED)
            .unRegisterAt(LocalDateTime.now())
            .build();
    }

}

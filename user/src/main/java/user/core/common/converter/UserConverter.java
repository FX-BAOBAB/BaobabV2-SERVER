package user.core.common.converter;

import global.annotation.Converter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.input.web.request.UserUnRegisterRequest;
import user.adapter.input.web.response.UserInfoResponse;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.Account;
import user.adapter.output.persistence.repository.Address;
import user.adapter.output.persistence.repository.UserDocument;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.command.UserUnRegisterCommand;
import user.domain.dto.UserRegisterForm;
import user.domain.dto.UserUnRegisterForm;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public UserRegisterCommand toRegisterCommand(UserRegisterRequest userRegisterRequest) {
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
            .build();
    }

    public UserDocument toUserDocument(UserRegisterForm userRegisterForm) {
        return UserDocument.builder()
            .nickName(userRegisterForm.getNickName())
            .phone(userRegisterForm.getPhone())
            .department(userRegisterForm.getDepartment())
            .birth(userRegisterForm.getBirth())
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
            .unRegisteredAt(user.getUnRegisteredAt())
            .lastLoginAt(user.getLastLoginAt())
            .build();
    }

    public UserRegisterForm toUserRegisterForm(UserRegisterCommand userRegisterCommand) {
        return UserRegisterForm.builder()
            .email(userRegisterCommand.getEmail())
            .encodingPassword(
                BCrypt.hashpw(userRegisterCommand.getPassword(), BCrypt.gensalt())
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
            .role(UserRole.BASIC_USER)
            .status(UserStatus.REGISTERED)
            .registeredAt(LocalDateTime.now())
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

    public UserInfoResponse toResponse(UserReaderCommand userReaderCommand) {
        return UserInfoResponse.builder()
            .userId(userReaderCommand.getUserId())
            .email(userReaderCommand.getEmail())
            .nickName(userReaderCommand.getNickName())
            .name(userReaderCommand.getName())
            .phone(userReaderCommand.getPhone())
            .department(userReaderCommand.getDepartment())
            .birth(userReaderCommand.getBirth())
            .address(userReaderCommand.getAddress())
            .detailAddress(userReaderCommand.getDetailAddress())
            .basicAddress(userReaderCommand.getBasicAddress())
            .post(userReaderCommand.getPost())
            .profileImage(userReaderCommand.getProfileImage())
            .role(userReaderCommand.getRole())
            .status(userReaderCommand.getStatus())
            .registeredAt(userReaderCommand.getRegisteredAt())
            .unRegisteredAt(userReaderCommand.getUnRegisteredAt())
            .lastLoginAt(userReaderCommand.getLastLoginAt())
            .build();
    }

}

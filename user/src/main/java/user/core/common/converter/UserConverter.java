package user.core.common.converter;

import global.annotation.Converter;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.input.web.request.UserUpdateRequest;
import user.adapter.output.persistence.repository.Account;
import user.adapter.output.persistence.repository.Address;
import user.adapter.output.persistence.repository.User;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.command.UserUpdateCommand;

@Converter
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
            .imageId(userRegisterRequest.getImageId())
            .build();
    }

    public User toUser(UserRegisterCommand userRegisterCommand) {
        return User.builder()
            .nickName(userRegisterCommand.getNickName())
            .phone(userRegisterCommand.getPhone())
            .birth(userRegisterCommand.getBirth())
            .department(userRegisterCommand.getDepartment())
            .imageId(userRegisterCommand.getImageId())
            .role(userRegisterCommand.getRole())
            .status(userRegisterCommand.getStatus())
            .registeredAt(userRegisterCommand.getRegisteredAt())
            .account(Account.builder()
                .email(userRegisterCommand.getEmail())
                .password(userRegisterCommand.getPassword())
                .name(userRegisterCommand.getName())
                .build())
            .address(Address.builder()
                .address(userRegisterCommand.getAddress())
                .detailAddress(userRegisterCommand.getDetailAddress())
                .basicAddress(userRegisterCommand.getBasicAddress())
                .post(userRegisterCommand.getPost())
                .build())
            .build();
    }

    public User toUser(UserReaderCommand userReaderCommand) {
        return User.builder()
            .id(userReaderCommand.getUserId())
            .nickName(userReaderCommand.getNickName())
            .phone(userReaderCommand.getPhone())
            .birth(userReaderCommand.getBirth())
            .department(userReaderCommand.getDepartment())
            .imageId(userReaderCommand.getImageId())
            .role(userReaderCommand.getRole())
            .status(userReaderCommand.getStatus())
            .registeredAt(userReaderCommand.getRegisteredAt())
            .account(Account.builder()
                .email(userReaderCommand.getEmail())
                .password(userReaderCommand.getPassword())
                .name(userReaderCommand.getName())
                .build())
            .address(Address.builder()
                .address(userReaderCommand.getAddress())
                .detailAddress(userReaderCommand.getDetailAddress())
                .basicAddress(userReaderCommand.getBasicAddress())
                .post(userReaderCommand.getPost())
                .build())
            .build();
    }

    public UserReaderCommand toReaderCommand(User user) {
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
            .imageId(user.getImageId())
            .role(user.getRole())
            .status(user.getStatus())
            .registeredAt(user.getRegisteredAt())
            .unregisteredAt(user.getUnregisteredAt())
            .lastLoginAt(user.getLastLoginAt())
            .build();
    }

    public UserUpdateCommand toUpdateCommand(UserUpdateRequest userUpdateRequest, String userId) {
        return UserUpdateCommand.builder()
            .userId(userId)
            .password(userUpdateRequest.getPassword())
            .nickName(userUpdateRequest.getNickName())
            .name(userUpdateRequest.getName())
            .phone(userUpdateRequest.getPhone())
            .birth(userUpdateRequest.getBirth())
            .department(userUpdateRequest.getDepartment())
            .address(userUpdateRequest.getAddress())
            .detailAddress(userUpdateRequest.getDetailAddress())
            .basicAddress(userUpdateRequest.getBasicAddress())
            .post(userUpdateRequest.getPost())
            .imageId(userUpdateRequest.getImageId())
            .build();

    }
}

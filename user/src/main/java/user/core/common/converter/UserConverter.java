package user.core.common.converter;

import global.annotation.Converter;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.output.persistence.repository.User;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;

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
            .email(userRegisterCommand.getEmail())
            .password(userRegisterCommand.getPassword())
            .nickName(userRegisterCommand.getNickName())
            .name(userRegisterCommand.getName())
            .phone(userRegisterCommand.getPhone())
            .birth(userRegisterCommand.getBirth())
            .department(userRegisterCommand.getDepartment())
            .address(userRegisterCommand.getAddress())
            .detailAddress(userRegisterCommand.getDetailAddress())
            .basicAddress(userRegisterCommand.getBasicAddress())
            .post(userRegisterCommand.getPost())
            .imageId(userRegisterCommand.getImageId())
            .role(userRegisterCommand.getRole())
            .status(userRegisterCommand.getStatus())
            .registeredAt(userRegisterCommand.getRegisteredAt())
            .build();
    }

    public UserReaderCommand toReaderCommand(User user) {
        return UserReaderCommand.builder()
            .userId(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .nickName(user.getNickName())
            .name(user.getNickName())
            .phone(user.getPhone())
            .department(user.getDepartment())
            .birth(user.getBirth())
            .address(user.getAddress())
            .detailAddress(user.getDetailAddress())
            .basicAddress(user.getBasicAddress())
            .post(user.getPost())
            .imageId(user.getImageId())
            .role(user.getRole())
            .status(user.getStatus())
            .registeredAt(user.getRegisteredAt())
            .unregisteredAt(user.getUnregisteredAt())
            .lastLoginAt(user.getLastLoginAt())
            .build();
    }

}

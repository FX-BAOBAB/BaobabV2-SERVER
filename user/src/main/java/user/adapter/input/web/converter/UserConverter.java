package user.adapter.input.web.converter;

import global.annotation.Converter;
import user.adapter.input.web.request.UserRegisterRequest;
import user.domain.command.UserRegisterCommand;

@Converter
public class UserConverter {

    public UserRegisterCommand toCommand(UserRegisterRequest userRegisterRequest) {
        return UserRegisterCommand.builder()
            .email(userRegisterRequest.getEmail())
            .password(userRegisterRequest.getPassword())
            .name(userRegisterRequest.getName())
            .birth(userRegisterRequest.getBirth())
            .department(userRegisterRequest.getDepartment())
            .address(userRegisterRequest.getAddress())
            .detailAddress(userRegisterRequest.getDetailAddress())
            .basicAddress(userRegisterRequest.getBasicAddress())
            .post(userRegisterRequest.getPost())
            .imageId(userRegisterRequest.getImageId())
            .build();
    }

}

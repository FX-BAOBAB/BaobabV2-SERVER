package user.domain.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.output.persistence.enums.GenderType;
import global.user.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;

@Data
@Builder
@AllArgsConstructor
public class UserRegisterCommand {

    private UserAccount userAccount;

    private String nickName;

    private UserPhoneInfo userPhoneInfo;

    private GenderType genderType;

    private Boolean isForeigner;

    private LocalDate birth;

    private UserAddress userAddress;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    public static UserRegisterCommand of(UserRegisterRequest userRegisterRequest) {
        return UserRegisterCommand.builder()
            .userAccount(UserAccount.builder()
                .email(userRegisterRequest.getEmail())
                .password(userRegisterRequest.getPassword())
                .name(userRegisterRequest.getNickName())
                .build())
            .nickName(userRegisterRequest.getNickName())
            .userPhoneInfo(UserPhoneInfo.builder()
                .carrierType(userRegisterRequest.getCarrierType())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .build())
            .genderType(userRegisterRequest.getGenderType())
            .isForeigner(userRegisterRequest.getIsForeigner())
            .birth(userRegisterRequest.getBirth())
            .userAddress(UserAddress.builder()
                .address(userRegisterRequest.getAddress())
                .detailAddress(userRegisterRequest.getDetailAddress())
                .basicAddress(userRegisterRequest.getBasicAddress())
                .post(userRegisterRequest.getPost())
                .build())
            .build();
    }

}
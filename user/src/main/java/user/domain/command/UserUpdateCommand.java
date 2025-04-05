package user.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import user.adapter.input.web.request.UserUpdateRequest;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;

@Data
@Builder
@AllArgsConstructor
public class UserUpdateCommand {

    private String userId;

    private String nickName;

    private UserPhoneInfo userPhoneInfo;

    private UserAddress userAddress;

    private MultipartFile profileImage;

    private String deleteImageId;

    public static UserUpdateCommand of(
        UserUpdateRequest userUpdateRequest, MultipartFile profileImage, String userId
    ) {
        return UserUpdateCommand.builder()
            .userId(userId)
            .nickName(userUpdateRequest.getNickName())
            .userPhoneInfo(UserPhoneInfo.builder()
                .carrierType(userUpdateRequest.getCarrierType())
                .phoneNumber(userUpdateRequest.getPhoneNumber())
                .build())
            .userAddress(UserAddress.builder()
                .address(userUpdateRequest.getAddress())
                .basicAddress(userUpdateRequest.getBasicAddress())
                .detailAddress(userUpdateRequest.getDetailAddress())
                .post(userUpdateRequest.getPost())
                .build())
            .profileImage(profileImage)
            .build();
    }

}

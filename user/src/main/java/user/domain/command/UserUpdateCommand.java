package user.domain.command;

import file.domain.ImageMetaData;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import user.adapter.input.web.request.UserUpdateRequest;
import user.domain.dto.ProfileImage;

@Data
@Builder
public class UserUpdateCommand {

    private String userId;

    private String nickName;

    private String name;

    private String phone;

    private LocalDate birth;

    private String department;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private ProfileImage profileImage;

    public static UserUpdateCommand toCommand(
        UserUpdateRequest userUpdateRequest, ImageMetaData imageMetaData, String userId
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

}

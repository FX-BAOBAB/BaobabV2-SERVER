package user.domain.command;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import user.adapter.input.web.request.UserUpdateRequest;

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

    private MultipartFile profileImage;

    private String deleteImageId;

    public static UserUpdateCommand of(
        UserUpdateRequest userUpdateRequest, MultipartFile profileImage, String userId
    ) {
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
            .profileImage(profileImage)
            .deleteImageId(userUpdateRequest.getDeleteImageId())
            .build();
    }

}

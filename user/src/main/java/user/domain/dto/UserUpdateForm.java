package user.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.domain.command.UserUpdateCommand;

@Data
@Builder
public class UserUpdateForm {

    private String userId;

    private String name;

    private String nickName;

    private String phone;

    private String department;

    private LocalDate birth;

    private ProfileImage profileImage;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    public static UserUpdateForm toForm(UserUpdateCommand userUpdateCommand) {
        return UserUpdateForm.builder()
            .userId(userUpdateCommand.getUserId())
            .nickName(userUpdateCommand.getNickName())
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

}

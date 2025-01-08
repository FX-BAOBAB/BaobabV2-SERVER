package user.adapter.input.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?!.*[\\\\{}()<>$%^&*_=|`]).{8,100}$",
        message = "대문자, 소문자, 특수문자를 포함하고 8자 이상이어야 합니다."
    )
    private String password;

    @Pattern(
        regexp = "^(?!.*[\\\\{}()<>$%^&*_=|`]).{2,50}$",
        message = "2자 이상 50자 이하이며, 특정 특수문자는 제외됩니다."
    )
    private String nickName;

    @Size(min = 1, max = 50, message = "이름은 1자 이상, 50자 이하로 입력해주세요.")
    private String name;

    @Pattern(
        regexp = "^01[016789]-\\d{3,4}-\\d{4}$",
        message = "올바른 전화번호 형식을 입력하세요 (예: 010-1234-5678)"
    )
    private String phone;

    private LocalDate birth;

    // TODO ENUM 처리 고려
    private String department;

    @NotBlank(message = "필수 입력 사항입니다.")
    @Size(max = 200, message = "최대 200자까지 입력 가능합니다.")
    private String address;

    @NotBlank(message = "필수 입력 사항입니다.")
    @Size(max = 200, message = "최대 200자까지 입력 가능합니다.")
    private String detailAddress;

    @NotBlank(message = "필수 입력 사항입니다.")
    @Size(max = 200, message = "최대 200자까지 입력 가능합니다.")
    private String basicAddress;

    @Pattern(
        regexp = "^[0-9]{5}$",
        message = "우편번호는 5자리 숫자여야 합니다."
    )
    private String post;

    private String imageId;

}


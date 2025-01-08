package user.adapter.input.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Pattern(
        regexp = "^(?!.*[\\\\{}()<>$%^&*_=|`]).{2,50}$",
        message = "2자 이상 50자 이하이며, 특정 특수문자는 제외됩니다."
    )
    private String nickName;

    @Pattern(
        regexp = "^01[016789]-\\d{3,4}-\\d{4}$",
        message = "올바른 전화번호 형식을 입력하세요 (예: 010-1234-5678)"
    )
    private String phone;

    private LocalDate birth;

    // TODO ENUM 처리 고려
    private String department;

    private String imageId;

    @Valid
    private AccountUpdateRequest account;

    @Valid
    private AddressUpdateRequest address;

}

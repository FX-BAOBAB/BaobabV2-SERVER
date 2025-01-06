package user.adapter.input.web.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuplicationNickNameRequest {

    @Pattern(
        regexp = "^(?!.*[\\\\{}()<>$%^&*_=|`]).{2,50}$",
        message = "2자 이상 50자 이하이며, 특정 특수문자는 제외됩니다."
    )
    private String nickName;

}

package user.adapter.input.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationRequest {

    @Pattern(
        regexp = "^[0-9a-zA-Z]{1,50}@[0-9a-zA-Z]{1,24}+(\\.[0-9a-zA-Z]+){1,24}$",
        message = "올바른 이메일 형식을 입력하세요 (예: example@domain.com)"
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String email;

    @Pattern(
        regexp = "^\\d{5}$",
        message = "5자리 숫자 인증번호를 입력하세요."
    )
    @NotBlank(message = "인증번호는 필수 입력 사항입니다.")
    private String verificationCode;

}

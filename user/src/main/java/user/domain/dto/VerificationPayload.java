package user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.domain.form.UserRegisterForm;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationPayload {

    private String verificationCode;
    private UserRegisterForm userInfo;

    public static VerificationPayload of(String verificationCode, UserRegisterForm userInfo) {
        return VerificationPayload.builder()
            .verificationCode(verificationCode)
            .userInfo(userInfo)
            .build();
    }

}

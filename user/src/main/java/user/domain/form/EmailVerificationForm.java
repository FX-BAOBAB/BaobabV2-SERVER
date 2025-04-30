package user.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.input.web.request.EmailVerificationRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationForm {

    private String email;

    private String verificationCode;

    public static EmailVerificationForm of(EmailVerificationRequest emailVerificationRequest) {
        return EmailVerificationForm.builder()
            .email(emailVerificationRequest.getEmail())
            .verificationCode(emailVerificationRequest.getVerificationCode())
            .build();
    }

}

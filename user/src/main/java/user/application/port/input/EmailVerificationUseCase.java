package user.application.port.input;

import user.domain.form.EmailVerificationForm;

public interface EmailVerificationUseCase {

    Boolean verifyEmail(EmailVerificationForm emailVerificationForm);

}

package user.application;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.application.port.input.EmailVerificationUseCase;
import user.application.port.output.RedisCachePort;
import user.application.port.output.UserPersistencePort;
import user.core.common.error.UserErrorCode;
import user.core.common.exception.user.EmailVerificationCodeMismatchException;
import user.core.common.exception.user.EmailVerificationExpiredException;
import user.domain.dto.VerificationPayload;
import user.domain.form.EmailVerificationForm;
import user.domain.form.UserRegisterForm;

@Service
@RequiredArgsConstructor
public class EmailVerificationService implements EmailVerificationUseCase {

    private final UserPersistencePort userPersistencePort;
    private final RedisCachePort redisCachePort;
    private final MailService mailService;

    private static final long REDIS_TTL = 5;
    private static final Random random = new Random();
    private static final DecimalFormat formatter = new DecimalFormat("00000");

    @Override
    public Boolean verifyEmail(EmailVerificationForm verificationForm) {
        String uniqueKey = getVerificationKey(verificationForm.getEmail());

        Optional<VerificationPayload> optionalPayload = redisCachePort.getVerificationData(
            uniqueKey);

        VerificationPayload payload = optionalPayload.orElseThrow(() ->
            new EmailVerificationExpiredException(UserErrorCode.EMAIL_VERIFICATION_EXPIRED)
        );

        if (!payload.getVerificationCode().equals(verificationForm.getVerificationCode())) {
            throw new EmailVerificationCodeMismatchException(
                UserErrorCode.EMAIL_VERIFICATION_CODE_MISMATCH);
        }

        userPersistencePort.saveUser(payload.getUserInfo());
        redisCachePort.delete(uniqueKey);

        return true;
    }

    public void sendVerification(UserRegisterForm registerForm) {
        String verificationCode = generateCode();
        String uniqueKey = getVerificationKey(registerForm.getUserAccount().getEmail());

        VerificationPayload payload = VerificationPayload.of(verificationCode, registerForm);

        redisCachePort.save(uniqueKey, payload, REDIS_TTL);
        mailService.sendMail(registerForm.getUserAccount().getEmail(), verificationCode);
    }

    private static String generateCode() {
        int number = random.nextInt(100000); // 0 ~ 99999
        return formatter.format(number);
    }

    private String getVerificationKey(String email) {
        return "email:verification:" + email;
    }


}

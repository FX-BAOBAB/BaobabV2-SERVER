package user.application;

import java.text.DecimalFormat;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import user.application.port.input.UserRegisterUseCase;
import user.application.port.output.RedisCachePort;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserRegisterCommand;
import user.domain.dto.VerificationPayload;
import user.domain.form.UserRegisterForm;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {

    private final UserPersistencePort userPersistencePort;
    private final RedisCachePort redisCachePort;
    private final MailService mailService;

    private static final long REDIS_TTL = 5;
    private static final Random random = new Random();
    private static final DecimalFormat formatter = new DecimalFormat("00000");

    @Override
    public Boolean register(UserRegisterCommand userRegisterCommand) {

        // 비밀번호 암호화
        String encryptedPassword = BCrypt.hashpw(userRegisterCommand.getUserAccount().getPassword(),
            BCrypt.gensalt());

        UserRegisterForm registerForm = UserRegisterForm.of(userRegisterCommand,
            encryptedPassword);

        sendVerification(registerForm);

        return true;
    }

    private void sendVerification(UserRegisterForm registerForm) {
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

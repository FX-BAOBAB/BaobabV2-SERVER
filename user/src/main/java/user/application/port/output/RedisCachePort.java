package user.application.port.output;

import java.util.Optional;
import user.domain.dto.VerificationPayload;
import user.domain.form.UserRegisterForm;

public interface RedisCachePort {

    void save(String uniqueKey, VerificationPayload payload, long ttlMinutes);

    Optional<UserRegisterForm> getVerificationData(String uniqueKey);

}

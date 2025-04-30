package user.adapter.output.persistence;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import user.application.port.output.RedisCachePort;
import user.domain.dto.VerificationPayload;
import user.domain.form.UserRegisterForm;

@Component
@RequiredArgsConstructor
public class RedisCacheAdapter implements RedisCachePort {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(String uniqueKey, VerificationPayload payload, long ttlMinutes) {
        redisTemplate.opsForValue().set(uniqueKey, payload, ttlMinutes, TimeUnit.MINUTES);
    }

    @Override
    public Optional<UserRegisterForm> getVerificationData(String uniqueKey) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(uniqueKey))
            .filter(UserRegisterForm.class::isInstance)
            .map(UserRegisterForm.class::cast);
    }

}

package user.adapter.output.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import user.application.port.output.RedisCachePort;
import user.domain.dto.VerificationPayload;

@Component
@RequiredArgsConstructor
public class RedisCacheAdapter implements RedisCachePort {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;

    @Override
    public void save(String uniqueKey, VerificationPayload payload, long ttlMinutes) {
        redisTemplate.opsForValue().set(uniqueKey, payload, ttlMinutes, TimeUnit.MINUTES);
    }

    @Override
    public Optional<VerificationPayload> getVerificationData(String uniqueKey) {
        Object rawValue = redisTemplate.opsForValue().get(uniqueKey);
        if (rawValue == null) {
            return Optional.empty();
        }

        try {
            VerificationPayload payload = redisObjectMapper.convertValue(rawValue, VerificationPayload.class);
            return Optional.of(payload);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(String uniqueKey) {
        redisTemplate.delete(uniqueKey);
    }

}

package chat.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import javax.management.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(String channel, Object message) {
        log.info("Publishing message to channel: [{}] at time: {} with message: {}", channel, Instant.now(), message);
        redisTemplate.convertAndSend(channel, message);
        log.info("Published message to channel: [{}] at time: {} with message: {}", channel, Instant.now(), message);
    }

    public void saveNotificationWithTTL(String key, Notification notification, long ttl, TimeUnit timeUnit) {
        try {
            String notificationJson = objectMapper.writeValueAsString(notification);
            redisTemplate.opsForValue().set(key, notificationJson, ttl, timeUnit);
            log.debug("Saved notification with key: {} and TTL: {} {}", key, ttl, timeUnit);
        } catch (Exception e) {
            log.error("Error saving notification with key: {} and TTL: {} {}", key, ttl, timeUnit, e);
        }
    }

}

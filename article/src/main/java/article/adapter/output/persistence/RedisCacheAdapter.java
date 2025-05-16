package article.adapter.output.persistence;

import article.application.port.output.RedisCachePort;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheAdapter implements RedisCachePort {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean hasUniqueKey(String uniqueKey) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(uniqueKey));
    }

    @Override
    public void markArticleAsViewed(String uniqueKey, long ttlMinutes) {
        redisTemplate.opsForValue().set(uniqueKey, "read", Duration.ofMinutes(ttlMinutes));
    }

    @Override
    public void incrementArticleViewCount(String articleViewCountKey, int ttlMinutes) {
        redisTemplate.opsForValue().increment(articleViewCountKey);
        redisTemplate.expire(articleViewCountKey, Duration.ofMinutes(ttlMinutes));
    }

    @Override
    public List<String> getKeysByPrefix(String prefix) {
        ScanOptions scanOptions = ScanOptions.scanOptions().match(prefix + "*").count(100).build();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);

        List<String> keys = new ArrayList<>();
        while (cursor.hasNext()) {
            keys.add(new String(cursor.next()));
        }
        return keys;
    }

    @Override
    public String getValueBy(String uniqueKey) {
        return redisTemplate.opsForValue().get(uniqueKey);
    }

    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

}

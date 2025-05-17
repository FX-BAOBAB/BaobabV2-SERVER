package article.application.port.output;

import java.util.List;

public interface RedisCachePort {

    boolean hasUniqueKey(String uniqueKey);

    void markArticleAsViewed(String uniqueKey, long ttlMinutes);

    void incrementArticleViewCount(String articleViewCountKey, int ttlMinutes);

    List<String> getKeysByPrefix(String prefix);

    String getValueBy(String uniqueKey);

    void deleteKey(String key);

}

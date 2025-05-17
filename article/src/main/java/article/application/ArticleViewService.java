package article.application;

import article.adapter.output.persistence.repository.Article;
import article.application.port.output.ArticlePersistencePort;
import article.application.port.output.RedisCachePort;
import article.application.util.ClientIpUtil;
import article.domain.dto.ArticleUpdateForm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleViewService {

    private final RedisCachePort redisCachePort;
    private final ArticlePersistencePort articlePersistencePort;

    private final HttpServletRequest request;
    private final ClientIpUtil clientIpUtil;

    private static final String ARTICLE_VIEWED_KEY_PREFIX = "article:viewed:user:";
    private static final String ARTICLE_VIEW_COUNT_KEY_PREFIX = "article:view:count:";

    /**
     * userId is nullable
     *
     * @param articleId
     * @param userId
     */
    @Async
    public void increaseViewCount(String articleId, String userId) {
        String viewedArticleKey = generateViewedArticleKey(articleId, userId);
        if (hasViewedArticle(viewedArticleKey)) {
            return;
        }

        String articleViewCountKey = generateArticleViewCountKey(articleId);
        redisCachePort.markArticleAsViewed(viewedArticleKey, 1);
        redisCachePort.incrementArticleViewCount(articleViewCountKey, 2);
    }

    private boolean hasViewedArticle(String uniqueKey) {
        return redisCachePort.hasUniqueKey(uniqueKey);
    }

    private String generateViewedArticleKey(String articleId, String userId) {
        if (userId == null) {
            String ip = clientIpUtil.getClientIp(request);
            String userAgent = request.getHeader("User-Agent");
            String fingerprint = ip + ":" + userAgent;
            return ARTICLE_VIEWED_KEY_PREFIX + articleId + ":" + fingerprint.hashCode();
        }
        return ARTICLE_VIEWED_KEY_PREFIX + articleId + ":" + userId;
    }

    private String generateArticleViewCountKey(String articleId) {
        return ARTICLE_VIEW_COUNT_KEY_PREFIX + articleId;
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void syncViewCountToDatabase() {
        List<String> viewCountKeys = redisCachePort.getKeysByPrefix(ARTICLE_VIEW_COUNT_KEY_PREFIX);

        if (viewCountKeys.isEmpty()) {
            return;
        }

        Map<String, Long> viewCountMap = extractViewCountsFromRedis(viewCountKeys);

        // 성능저하 우려
        List<Article> articleList = articlePersistencePort.getArticlesBy(
            new ArrayList<>(viewCountMap.keySet()));

        for (Article article : articleList) {
            article.setViewCount(article.getViewCount() + viewCountMap.get(article.getId()));
            redisCachePort.deleteKey(ARTICLE_VIEW_COUNT_KEY_PREFIX + article.getId());
        }
        articlePersistencePort.updateArticles(ArticleUpdateForm.of(articleList));

    }

    private Map<String, Long> extractViewCountsFromRedis(List<String> viewCountKeys) {
        return viewCountKeys.stream()
            .map(key -> {
                String articleId = key.replace(ARTICLE_VIEW_COUNT_KEY_PREFIX, "");
                String value = redisCachePort.getValueBy(key);
                return (value != null) ? Map.entry(articleId, Long.parseLong(value)) : null;
            })
            .filter(entry -> entry != null)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}

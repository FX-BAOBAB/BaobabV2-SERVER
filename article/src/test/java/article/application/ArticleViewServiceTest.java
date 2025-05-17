package article.application;

import static org.mockito.Mockito.*;

import article.adapter.output.persistence.repository.Article;
import article.application.port.output.ArticlePersistencePort;
import article.application.port.output.RedisCachePort;
import article.application.util.ClientIpUtil;
import article.domain.dto.ArticleUpdateForm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleViewServiceTest {

    @Mock
    private RedisCachePort redisCachePort;

    @Mock
    private ArticlePersistencePort articlePersistencePort;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ClientIpUtil clientIpUtil;

    @InjectMocks
    private ArticleViewService articleViewService;

    @Test
    void 조회수_증가_성공_userId_있는_경우() {

        // Given
        String articleId = "article123";
        String userId = "user456";
        String viewedArticleKey = "article:viewed:user:" + articleId + ":" + userId;
        String articleViewCountKey = "article:view:count:" + articleId;

        when(redisCachePort.hasUniqueKey(viewedArticleKey)).thenReturn(false);

        // When
        articleViewService.increaseViewCount(articleId, userId);

        // Then
        verify(redisCachePort, times(1)).hasUniqueKey(viewedArticleKey);
        verify(redisCachePort, times(1)).markArticleAsViewed(viewedArticleKey, 1);
        verify(redisCachePort, times(1)).incrementArticleViewCount(articleViewCountKey, 2);
    }

    @Test
    void 조회수_증가_성공_userId_없는_경우() {
        // Given
        String articleId = "article123";
        String ip = "123.123.123.123";
        String userAgent = "mockBrowser";

        when(clientIpUtil.getClientIp(request)).thenReturn(ip);
        when(request.getHeader("User-Agent")).thenReturn(userAgent);

        String fingerprint = ip + ":" + userAgent;
        String viewedArticleKey = "article:viewed:user:" + articleId + ":" + fingerprint.hashCode();
        String articleViewCountKey = "article:view:count:" + articleId;

        when(redisCachePort.hasUniqueKey(viewedArticleKey)).thenReturn(false);

        // When
        articleViewService.increaseViewCount(articleId, null);

        // Then
        verify(redisCachePort, times(1)).hasUniqueKey(viewedArticleKey);
        verify(redisCachePort, times(1)).markArticleAsViewed(viewedArticleKey, 1);
        verify(redisCachePort, times(1)).incrementArticleViewCount(articleViewCountKey, 2);
    }

    @Test
    void REDIS_TO_DB_동기화() {
        // Given
        String articleId = "article123";
        String redisKey = "article:view:count:" + articleId;
        String redisValue = "5";

        List<String> redisKeys = List.of(redisKey);

        Article article = new Article();
        article.setId(articleId);
        article.setViewCount(10L);

        when(redisCachePort.getKeysByPrefix("article:view:count:")).thenReturn(redisKeys);
        when(redisCachePort.getValueBy(redisKey)).thenReturn(redisValue);
        when(articlePersistencePort.getArticlesBy(List.of(articleId))).thenReturn(List.of(article));

        // When
        articleViewService.syncViewCountToDatabase();

        // Then
        verify(redisCachePort, times(1)).deleteKey(redisKey);
        verify(articlePersistencePort, times(1))
            .updateArticles(argThat(formList -> {
                ArticleUpdateForm form = formList.getFirst();
                return form.getViewCount() == 15L;
            }));

    }

}
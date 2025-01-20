package article.application.port.input;

import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleSearchCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Get Article Input Port
 */
public interface GetArticleUseCase {

    /**
     * Get Article By Article ID
     * @param articleId Article ID
     * @return Article
     */
    Article getArticleBy(String articleId);

    /**
     * Get My Article List By User ID
     * @param userId User ID
     * @param pageable Pageable
     * @return Article List
     */
    List<Article> getMyArticles(String userId, Pageable pageable);

    /**
     * Get Article List By Search Condition
     * @see ArticleSearchCommand
     * @param articleSearchCommand Article Search Condition
     * @return Article List
     */
    List<Article> getArticleList(ArticleSearchCommand articleSearchCommand);

}

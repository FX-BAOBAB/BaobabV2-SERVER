package article.application.port.input;

import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleSearchCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Get Article Input Port
 */
public interface GetArticleUseCase {

    /**
     * Get My Articles By Article ID
     * @param articleId Login Article ID
     * @return Article List
     */
    Article getArticlesBy(String articleId);

    /**
     * Get Article List By Search command
     * @param command search command
     * @return Article List
     */
    List<Article> getMyArticles(ArticleSearchCommand command);

    /**
     * Get Article List By Search Condition
     * @see ArticleSearchCommand
     * @param articleSearchCommand Article Search Condition
     * @return Article List
     */
    List<Article> getArticleList(ArticleSearchCommand articleSearchCommand);

}

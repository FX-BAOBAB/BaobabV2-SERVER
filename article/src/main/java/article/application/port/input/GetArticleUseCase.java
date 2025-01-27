package article.application.port.input;

import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleSearchCommand;

import java.util.List;

/**
 * Get Article Input Port
 */
public interface GetArticleUseCase {

    /**
     * Get Article List By Search Condition
     * @see ArticleSearchCommand
     * @param articleSearchCommand Article Search Condition
     * @return Article List
     */
    List<Article> getArticleList(ArticleSearchCommand articleSearchCommand);

}

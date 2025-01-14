package article.application.port.input;

import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleSearchCommand;
import java.util.List;

/**
 * Get Article Input Port
 */
public interface GetArticleUseCase {

    /**
     * Get Article By Article Id
     * @param articleId Article PK
     * @return Article
     */
    Article getArticleById(List<String> articleId);

    /**
     * Get Article List By Search Condition
     * @see ArticleSearchCommand
     * @param articleSearchCommand Article Search Condition
     * @return Article List
     */
    List<Article> getArticleList(ArticleSearchCommand articleSearchCommand);

    // TODO 노출 Algorithm 적용 필요
    /**
     * get ALL Article List
     * @return Article List
     */
    List<Article> getAllArticleList();

}

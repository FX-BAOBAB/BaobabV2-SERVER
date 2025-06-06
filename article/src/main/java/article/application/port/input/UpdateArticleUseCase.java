package article.application.port.input;

import article.domain.command.ArticleSaleStatusUpdateCommand;
import article.domain.command.ArticleUpdateCommand;

/**
 * Update Article INPUT Port
 */
public interface UpdateArticleUseCase {

    /**
     * Update Article Data
     * @param articleUpdateCommand User Input Article Update Data
     * @return is Article Update?
     */
    boolean updateArticle(ArticleUpdateCommand articleUpdateCommand);

    boolean updateArticleSaleStatus(ArticleSaleStatusUpdateCommand command);

}

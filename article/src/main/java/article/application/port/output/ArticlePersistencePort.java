package article.application.port.output;

import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import article.domain.dto.ArticleSaveForm;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Article Persistence OUTPUT Port
 */
public interface ArticlePersistencePort {

    /**
     * Save Article At DB
     * @param articleSaveForm Article Data
     * @return is Article saved?
     */
    boolean saveArticle(ArticleSaveForm articleSaveForm);

    /**
     * Get Article List By SearchCondition
     * @param articleSearchCommand Article Search Condition
     * @return Article
     */
    List<Article> getArticleList(ArticleSearchCommand articleSearchCommand);

    /**
     * Get Article List By User Id
     * @param userId User Id List
     * @return List<ArticleCommand>
     */
    List<Article> getMyArticles(String userId, Pageable pageable);

    /**
     * Get Article By Article Id
     * @param articleId articleId
     * @return Article
     */
    Optional<Article> getArticleById(String articleId);

    /**
     * Update Article Data
     * @param articleUpdateCommand Updated Article Data
     * @return is Article Updated?
     */
    boolean updateArticle(ArticleUpdateCommand articleUpdateCommand);

    /**
     * Delete Article Data
     * @param articleId Article PK
     * @return is Article Deleted?
     */
    boolean deleteArticle(String articleId);
}

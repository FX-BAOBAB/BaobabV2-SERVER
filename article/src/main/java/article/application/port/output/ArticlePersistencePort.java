package article.application.port.output;

import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleSearchCommand;
import article.domain.dto.ArticleSaveForm;
import article.domain.dto.ArticleUpdateForm;
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
    List<Article> getArticleList(ArticleSearchCommand articleSearchCommand, ArticleVisibilityStatus visibilityStatus);

    /**
     * Get Article By Article Id
     * @param articleId articleId
     * @return Article
     */
    Optional<Article> getArticleById(String articleId, ArticleVisibilityStatus visibilityStatus);

    /**
     * Update Article Data
     * @param articleUpdateForm Updated Article Data
     * @return is Article Updated?
     */
    boolean updateArticle(ArticleUpdateForm articleUpdateForm);

    /**
     * Delete Article Data
     * @param articleId Article PK
     * @return is Article Deleted?
     */
    boolean deleteArticle(String articleId);

    List<Article> getArticlesBy(List<String> articleIds);

    void updateArticles(List<ArticleUpdateForm> articleUpdateForms);


}

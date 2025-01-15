package article.application.port.output;

import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleCommand;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Article Persistence OUTPUT Port
 */
public interface ArticlePersistencePort {

    /**
     * Save Article At DB
     * @param articleSaveCommand Article Data
     * @return is Article saved?
     */
    boolean saveArticle(ArticleSaveCommand articleSaveCommand);

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
     * Update Article Data
     * @param article Updated Article Data
     * @return is Article Updated?
     */
    boolean updateArticle(Article article);

    /**
     * Delete Article Data
     * @param articleId Article PK
     * @return is Article Deleted?
     */
    boolean deleteArticle(String articleId);

    Optional<Article> findById(String articleId);
}

package article.application.port.output;

import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleCommand;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import java.util.List;

public interface ArticlePersistencePort {

    boolean saveArticle(ArticleSaveCommand articleSaveCommand);

    ArticleCommand getArticle(ArticleSearchCommand articleSearchCommand);

    List<ArticleCommand> getArticleList(ArticleSearchCommand articleSearchCommand);

    List<ArticleCommand> getAllArticleList();

    boolean updateArticle(Article article);

    boolean deleteArticle(String articleId);

}

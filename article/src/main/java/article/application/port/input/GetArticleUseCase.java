package article.application.port.input;

import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleSearchCommand;
import java.util.List;

public interface GetArticleUseCase {

    Article getArticleById(String articleId);

    Article getArticleByUserId(String userId);

    List<Article> getArticleList(ArticleSearchCommand articleSearchCommand);

    List<Article> getAllArticleList();

}

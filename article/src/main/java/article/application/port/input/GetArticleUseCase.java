package article.application.port.input;

import article.domain.command.ArticleCommand;
import article.domain.command.ArticleSearchCommand;
import java.util.List;

public interface GetArticleUseCase {

    ArticleCommand getArticleById(String articleId);

    ArticleCommand getArticleByUserId(String userId);

    List<ArticleCommand> getArticleList(ArticleSearchCommand articleSearchCommand);

    List<ArticleCommand> getAllArticleList();

}

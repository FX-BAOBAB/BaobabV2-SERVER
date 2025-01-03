package article.application.port.input;

import article.domain.command.ArticleSaveCommand;
import java.util.List;

public interface GetArticleUseCase {

    ArticleSaveCommand getArticleById(Long articleId);

    ArticleSaveCommand getArticleByTitle(String articleTitle);

    ArticleSaveCommand getArticleByContent(String articleContent);

    List<ArticleSaveCommand> getArticleList();

}

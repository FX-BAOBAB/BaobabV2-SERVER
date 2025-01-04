package article.application.port.input;

import article.domain.command.ArticleCommand;

public interface SaveArticleUseCase {

    boolean saveArticle(ArticleCommand articleSaveCommand);

}

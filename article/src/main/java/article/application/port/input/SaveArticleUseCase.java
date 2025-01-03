package article.application.port.input;

import article.domain.command.ArticleSaveCommand;

public interface SaveArticleUseCase {

    boolean saveArticle(ArticleSaveCommand articleSaveCommand);

}

package article.application.port.input;

import article.domain.command.ArticleUpdateCommand;

public interface UpdateArticleUseCase {

    boolean updateArticle(ArticleUpdateCommand articleUpdateCommand);

}

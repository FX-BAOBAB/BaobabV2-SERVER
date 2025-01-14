package article.application;

import article.adapter.output.persistence.enums.ArticleStatus;
import article.adapter.output.persistence.repository.Article;
import article.application.port.input.DefaultArticleUseCase;
import article.application.port.output.ArticlePersistencePort;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleSaveService implements DefaultArticleUseCase {

    private final ArticlePersistencePort articlePersistencePort;

    @Override
    public boolean saveArticle(ArticleSaveCommand articleSaveCommand) {

        articleSaveCommand.setStatus(ArticleStatus.ON_SALE);

        articleSaveCommand.setRegisteredAt(LocalDateTime.now());

        return articlePersistencePort.saveArticle(articleSaveCommand);
    }

    @Override
    public List<Article> getMyArticles(String userId) {
        return articlePersistencePort.getArticleListBy(userId);
    }

    @Override
    public boolean deleteArticle(String articleId) {
        return false;
    }

    @Override
    public List<Article> getArticlesBy(Long articleId) {
        return null;
    }

    @Override
    public List<Article> getArticleList(ArticleSearchCommand articleSearchCommand) {
        return articlePersistencePort.getArticleList(articleSearchCommand);
    }

    @Override
    public boolean updateArticle(ArticleUpdateCommand articleUpdateCommand) {
        return false;
    }
}

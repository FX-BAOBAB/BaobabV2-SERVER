package article.application;

import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.output.persistence.enums.ArticleStatus;
import article.adapter.output.persistence.repository.Article;
import article.application.port.input.DefaultArticleUseCase;
import article.application.port.output.ArticlePersistencePort;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public List<Article> getMyArticles(ArticleSearchCommand command) {
        return articlePersistencePort.getMyArticles(command.getUserId(),command.getPageable());
    }

    @Override
    public boolean deleteArticle(String articleId) {
        return false;
    }

    @Override
    public Article getArticlesBy(String articleId) {
        // TODO Exception 처리 필요
        return articlePersistencePort.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));
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

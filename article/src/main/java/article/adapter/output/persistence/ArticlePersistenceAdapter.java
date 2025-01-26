package article.adapter.output.persistence;

import article.adapter.output.persistence.repository.Article;
import article.adapter.output.persistence.repository.ArticleMongoRepository;
import article.adapter.output.persistence.repository.ArticleQueryRepository;
import article.application.port.output.ArticlePersistencePort;
import article.core.common.converter.ArticleConverter;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import article.domain.dto.ArticleSaveForm;
import global.annotation.output.PersistenceAdapter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@PersistenceAdapter
@RequiredArgsConstructor
public class ArticlePersistenceAdapter implements ArticlePersistencePort {

    private final ArticleConverter articleConverter;

    private final ArticleMongoRepository articleMongoRepository;

    private final ArticleQueryRepository articleQueryRepository;

    @Override
    public boolean saveArticle(ArticleSaveForm articleSaveForm) {
        Article savedArticle =  articleMongoRepository.save(Article.of(articleSaveForm));
        return savedArticle.getId() != null;
    }

    @Override
    public List<Article> getArticleList(ArticleSearchCommand articleSearchCommand) {
        return articleQueryRepository.getArticlesBy(articleSearchCommand);
    }

    @Override
    public List<Article> getMyArticles(String userId, Pageable pageable) {
        return articleMongoRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Optional<Article> getArticleById(String articleId) {
        return articleMongoRepository.findById(articleId);
    }

    @Override
    public boolean updateArticle(ArticleUpdateCommand articleUpdateCommand) {
        Article article = articleConverter.toArticle(articleUpdateCommand);
        Article updatedArticle =  articleMongoRepository.save(article);
        return updatedArticle.getId() != null;
    }

    @Override
    public boolean deleteArticle(String articleId) {
        articleMongoRepository.deleteById(articleId);
        Optional<Article> article = articleMongoRepository.findById(articleId);
        return article.isEmpty();
    }

}

package article.adapter.output.persistence;

import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.adapter.output.persistence.repository.Article;
import article.adapter.output.persistence.repository.ArticleMongoRepository;
import article.adapter.output.persistence.repository.ArticleQueryRepository;
import article.application.port.output.ArticlePersistencePort;
import article.domain.command.ArticleSearchCommand;
import article.domain.dto.ArticleSaveForm;
import article.domain.dto.ArticleUpdateForm;
import global.annotation.output.PersistenceAdapter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ArticlePersistenceAdapter implements ArticlePersistencePort {

    private final ArticleMongoRepository articleMongoRepository;

    private final ArticleQueryRepository articleQueryRepository;

    @Override
    public boolean saveArticle(ArticleSaveForm articleSaveForm) {
        Article savedArticle =  articleMongoRepository.save(Article.of(articleSaveForm));
        return savedArticle.getId() != null;
    }

    @Override
    public List<Article> getArticleList(ArticleSearchCommand articleSearchCommand,
        ArticleVisibilityStatus visibilityStatus) {
        return articleQueryRepository.getArticlesBy(articleSearchCommand, visibilityStatus);
    }

    @Override
    public Optional<Article> getArticleById(String articleId,
        ArticleVisibilityStatus visibilityStatus) {
        return articleMongoRepository.findByIdAndVisibilityStatus(articleId, visibilityStatus);
    }

    @Override
    public boolean updateArticle(ArticleUpdateForm articleUpdateForm) {
        Article updatedArticle =  articleMongoRepository.save(Article.of(articleUpdateForm));
        return updatedArticle.getId() != null;
    }

    @Override
    public boolean deleteArticle(String articleId) {
        articleMongoRepository.deleteById(articleId);
        Optional<Article> article = articleMongoRepository.findById(articleId);
        return article.isEmpty();
    }

    @Override
    public List<Article> getArticlesBy(List<String> articleIds) {
        return articleMongoRepository.findByIdInAndVisibilityStatus(articleIds,
            ArticleVisibilityStatus.VISIBILITY);
    }

    @Override
    public void updateArticles(List<ArticleUpdateForm> articleUpdateForms) {
        articleMongoRepository.saveAll(Article.of(articleUpdateForms));
    }

}

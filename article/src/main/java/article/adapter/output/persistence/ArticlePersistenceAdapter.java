package article.adapter.output.persistence;

import article.adapter.output.persistence.repository.Article;
import article.adapter.output.persistence.repository.ArticleMongoRepository;
import article.application.port.output.ArticlePersistencePort;
import article.core.common.converter.ArticleConverter;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
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

    @Override
    public boolean saveArticle(ArticleSaveCommand articleSaveCommand) {
        Article article = articleConverter.toArticle(articleSaveCommand);
        Article savedArticle =  articleMongoRepository.save(article);
        return savedArticle.getId() != null;
    }

    @Override
    public List<Article> getArticleList(ArticleSearchCommand articleSearchCommand) {
        // TODO Query DSL 적용 필요
        return articleMongoRepository.findAll();
    }

    @Override
    public List<Article> getMyArticles(String userId, Pageable pageable) {
        return articleMongoRepository.findAllByUserId(userId,pageable);
    }

    @Override
    public boolean updateArticle(Article article) {
        return false;
    }

    @Override
    public boolean deleteArticle(String articleId) {
        return false;
    }

    @Override
    public Optional<Article> findById(String articleId) {
        return articleMongoRepository.findById(articleId);
    }

}

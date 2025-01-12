package article.adapter.output.persistence;

import article.adapter.output.persistence.repository.Article;
import article.adapter.output.persistence.repository.ArticleMongoRepository;
import article.application.port.output.ArticlePersistencePort;
import article.core.common.converter.ArticleConverter;
import article.domain.command.ArticleCommand;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import global.annotation.output.PersistenceAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;

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
    public ArticleCommand getArticle(ArticleSearchCommand articleSearchCommand) {
        return null;
    }

    @Override
    public List<ArticleCommand> getArticleList(ArticleSearchCommand articleSearchCommand) {
        return List.of();
    }

    @Override
    public List<ArticleCommand> getAllArticleList() {
        return List.of();
    }

    @Override
    public boolean updateArticle(Article article) {
        return false;
    }

    @Override
    public boolean deleteArticle(String articleId) {
        return false;
    }

}

package article.application.port.output;

import article.adapter.output.persistence.Article;
import java.util.List;

public interface ArticlePersistencePort {

    boolean saveArticle(Article article);

    Article getArticle(Article article);

    List<Article> getArticleList(Article article);

    List<Article> getAllArticleList();

    boolean updateArticle(Article article);

    boolean deleteArticle(String articleId);

}

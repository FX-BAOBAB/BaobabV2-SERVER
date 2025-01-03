package article.application.port.output;

import article.adapter.output.persistence.Article;
import java.util.List;

public interface GetArticlePort {

    Article getArticleById(String articleId);

    Article getArticleByTitle(String title);

    Article getArticleByContent(String content);

    List<Article> getArticleList();

}

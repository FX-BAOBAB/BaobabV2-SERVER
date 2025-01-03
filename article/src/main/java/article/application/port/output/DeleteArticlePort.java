package article.application.port.output;

import article.adapter.output.persistence.Article;

public interface DeleteArticlePort {

    boolean deleteArticle(String articleId);

}

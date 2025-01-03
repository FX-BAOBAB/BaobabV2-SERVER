package article.application.port.output;

import article.adapter.output.persistence.Article;

public interface UpdateArticlePort {

    boolean updateArticle(Article article);

}

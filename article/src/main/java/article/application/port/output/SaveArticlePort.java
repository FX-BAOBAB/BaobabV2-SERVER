package article.application.port.output;

import article.adapter.output.persistence.Article;

public interface SaveArticlePort {

    boolean saveArticle(Article article);

}

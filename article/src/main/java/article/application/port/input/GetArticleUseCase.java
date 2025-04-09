package article.application.port.input;

import article.adapter.input.web.response.ArticleFeignResponse;
import article.adapter.input.web.response.ArticleInfoResponse;
import article.domain.command.ArticleSearchCommand;

import java.util.List;

/**
 * Get Article Input Port
 */
public interface GetArticleUseCase {

    /**
     * Get Article List By Search Condition
     *
     * @param articleSearchCommand Article Search Condition
     * @return ArticleInfoResponse List
     */
    List<ArticleInfoResponse> getArticleList(ArticleSearchCommand articleSearchCommand, String userId);

    ArticleFeignResponse getArticleBy(String articleId);

}

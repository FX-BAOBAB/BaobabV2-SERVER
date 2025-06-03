package article.application.port.input;

import article.domain.dto.ArticleImage;
import java.util.List;

/**
 * Article Delete Input Port
 */
public interface DeleteArticleUseCase {

    /**
     * Delete Article Method
     * @param articleId Article ID
     * @param userId User ID
     * @return is Article Delete?
     */
    boolean softDeleteArticle(String articleId, String userId);

    void hardDeleteArticle(String articleId, List<ArticleImage> articleIamgeList);

}

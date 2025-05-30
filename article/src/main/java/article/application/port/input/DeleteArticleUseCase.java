package article.application.port.input;

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

}

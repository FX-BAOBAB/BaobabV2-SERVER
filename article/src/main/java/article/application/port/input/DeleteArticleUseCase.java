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
    boolean deleteArticle(String articleId, String userId);

}

package article.application.port.input;

/**
 * Article Delete Input Port
 */
public interface DeleteArticleUseCase {

    /**
     * Delete Article Method
     * @param articleId Article PK
     * @return is Article Delete?
     */
    boolean deleteArticle(String articleId);

}

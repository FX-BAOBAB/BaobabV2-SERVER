package article.application.port.input;

/**
 * Article Bookmark Input Port
 */
public interface BookmarkArticleUseCase {

    boolean bookmarkArticle(String articleId, String userId);

    boolean unbookmarkArticle(String articleId, String userId);

}

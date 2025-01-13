package article.application.port.input;

import article.domain.command.ArticleSaveCommand;

/**
 * Save Article INPUT Port
 */
public interface SaveArticleUseCase {

    /**
     * Save Article Input Port
     * @param articleSaveCommand User Input Article Data
     * @return is Article Save?
     */
    boolean saveArticle(ArticleSaveCommand articleSaveCommand);

}

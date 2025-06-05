package article.application.port.input;

import article.domain.command.ArticleReportSaveCommand;

public interface SaveArticleReportUseCase {

    boolean saveArticleReport(ArticleReportSaveCommand articleReportSaveCommand, String userId);

}

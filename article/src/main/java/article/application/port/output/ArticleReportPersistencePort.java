package article.application.port.output;

import article.domain.dto.ArticleReportSaveForm;

public interface ArticleReportPersistencePort {

    boolean saveArticleReport(ArticleReportSaveForm articleReportSaveForm);

    boolean existsByArticleIdAndUserId(String articleId, String userId);


}

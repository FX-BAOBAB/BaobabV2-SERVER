package article.adapter.output.persistence;

import article.adapter.output.persistence.repository.ArticleReport;
import article.adapter.output.persistence.repository.ArticleReportRepository;
import article.application.port.output.ArticleReportPersistencePort;
import article.domain.dto.ArticleReportSaveForm;
import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ArticleReportPersistenceAdapter implements ArticleReportPersistencePort {

    private final ArticleReportRepository articleReportRepository;

    @Override
    public boolean saveArticleReport(ArticleReportSaveForm articleReportSaveForm) {
        ArticleReport savedReport = articleReportRepository.save(
            ArticleReport.of(articleReportSaveForm));
        return savedReport.getArticleId() != null;
    }

    @Override
    public boolean existsByArticleIdAndUserId(String articleId, String userId) {
        return articleReportRepository.existsByArticleIdAndUserId(articleId, userId);
    }
}

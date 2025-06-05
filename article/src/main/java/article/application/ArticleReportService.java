package article.application;

import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.adapter.output.persistence.repository.Article;
import article.application.port.input.SaveArticleReportUseCase;
import article.application.port.output.ArticlePersistencePort;
import article.application.port.output.ArticleReportPersistencePort;
import article.core.common.error.article.ArticleErrorCode;
import article.core.common.error.report.ArticleReportErrorCode;
import article.core.common.exception.article.ArticleNotFoundException;
import article.core.common.exception.report.ArticleReportDuplicationException;
import article.core.common.exception.report.SelfArticleReportNotAllowedException;
import article.domain.command.ArticleReportSaveCommand;
import article.domain.dto.ArticleReportSaveForm;
import article.domain.dto.ArticleUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleReportService implements SaveArticleReportUseCase {

    private final ArticlePersistencePort articlePersistencePort;
    private final ArticleReportPersistencePort articleReportPersistencePort;

    private static final Long MAX_REPORT_COUNT = 10L;

    @Transactional
    @Override
    public boolean saveArticleReport(ArticleReportSaveCommand saveCommand,
        String userId) {

        Article article = articlePersistencePort.getArticleById(saveCommand.getArticleId(),
                ArticleVisibilityStatus.VISIBILITY)
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        if (article.getUserId().equals(userId)) {
            throw new SelfArticleReportNotAllowedException(
                ArticleReportErrorCode.SELF_ARTICLE_REPORT_NOT_ALLOWED);
        }

        if (articleReportPersistencePort.existsByArticleIdAndUserId(saveCommand.getArticleId(),
            userId)) {
            throw new ArticleReportDuplicationException(
                ArticleReportErrorCode.DUPLICATE_ARTICLE_REPORT);
        }

        Long updatedReportCount = article.getReportCount() + 1;
        article.setReportCount(updatedReportCount);

        if (updatedReportCount >= MAX_REPORT_COUNT) {
            article.setVisibilityStatus(ArticleVisibilityStatus.REPORTED);
        }

        articlePersistencePort.updateArticle(ArticleUpdateForm.of(article));
        return articleReportPersistencePort.saveArticleReport(
            ArticleReportSaveForm.of(saveCommand, userId));
    }

}

package article.application;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.adapter.output.persistence.enums.ReportReasonType;
import article.adapter.output.persistence.repository.Article;
import article.application.port.output.ArticlePersistencePort;
import article.application.port.output.ArticleReportPersistencePort;
import article.core.common.exception.report.ArticleReportDuplicationException;
import article.core.common.exception.report.SelfArticleReportNotAllowedException;
import article.domain.command.ArticleReportSaveCommand;
import article.domain.dto.ArticleReportSaveForm;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleReportServiceTest {

    @Mock
    private ArticlePersistencePort articlePersistencePort;

    @Mock
    private ArticleReportPersistencePort articleReportPersistencePort;

    @InjectMocks
    private ArticleReportService articleReportService;

    @Test
    void 게시글_신고_성공() {

        // Given
        String articleId = "article123";
        String userId = "user123";

        ArticleReportSaveCommand saveCommand = ArticleReportSaveCommand.builder()
            .reasonType(ReportReasonType.FRAUD)
            .reason("사기입니다.")
            .articleId(articleId)
            .build();

        Article existsArticle = Article.builder()
            .id(articleId)
            .userId("articleOwnerId")
            .visibilityStatus(ArticleVisibilityStatus.VISIBILITY)
            .reportCount(0L)
            .build();

        when(articlePersistencePort.getArticleById(saveCommand.getArticleId(), ArticleVisibilityStatus.VISIBILITY)).thenReturn(
            Optional.of(existsArticle));

        when(articleReportPersistencePort.existsByArticleIdAndUserId(saveCommand.getArticleId(), userId)).thenReturn(false);

        when(articleReportPersistencePort.saveArticleReport(any())).thenReturn(true);


        // When
        articleReportService.saveArticleReport(saveCommand, userId);

        // Then
        ArgumentCaptor<ArticleReportSaveForm> captor = ArgumentCaptor.forClass(ArticleReportSaveForm.class);
        verify(articleReportPersistencePort, times(1)).saveArticleReport(captor.capture());

        ArticleReportSaveForm captured = captor.getValue();
        assertThat(captured.getArticleId()).isEqualTo(articleId);
        assertThat(captured.getUserId()).isEqualTo(userId);
        assertThat(captured.getReason()).isEqualTo("사기입니다.");
        assertThat(captured.getReasonType()).isEqualTo(ReportReasonType.FRAUD);
        assertThat(captured.getRegisteredAt()).isNotNull();

        assertThat(existsArticle.getReportCount()).isEqualTo(1);

        verify(articlePersistencePort, times(1)).getArticleById(articleId, ArticleVisibilityStatus.VISIBILITY);
        verify(articleReportPersistencePort, times(1)).existsByArticleIdAndUserId(articleId, userId);
    }

    @Test
    void 자신_게시글_신고() {
        // Given
        String articleId = "article123";
        String userId = "articleOwnerId";

        ArticleReportSaveCommand saveCommand = ArticleReportSaveCommand.builder()
            .reasonType(ReportReasonType.FRAUD)
            .reason("사기입니다.")
            .articleId(articleId)
            .build();

        Article existsArticle = Article.builder()
            .id(articleId)
            .userId(userId)
            .visibilityStatus(ArticleVisibilityStatus.VISIBILITY)
            .reportCount(0L)
            .build();

        when(articlePersistencePort.getArticleById(saveCommand.getArticleId(), ArticleVisibilityStatus.VISIBILITY)).thenReturn(
            Optional.of(existsArticle));

        // When & Then
        assertThatThrownBy(() -> articleReportService.saveArticleReport(saveCommand, userId))
            .isInstanceOf(SelfArticleReportNotAllowedException.class);
    }

    @Test
    void 이미_신고한_게시글() {
        // Given
        String articleId = "article123";
        String userId = "user123";

        ArticleReportSaveCommand saveCommand = ArticleReportSaveCommand.builder()
            .reasonType(ReportReasonType.FRAUD)
            .reason("사기입니다.")
            .articleId(articleId)
            .build();

        Article existsArticle = Article.builder()
            .id(articleId)
            .userId("articleOwnerId") // 작성자와 다름
            .visibilityStatus(ArticleVisibilityStatus.VISIBILITY)
            .reportCount(0L)
            .build();

        when(articlePersistencePort.getArticleById(saveCommand.getArticleId(), ArticleVisibilityStatus.VISIBILITY))
            .thenReturn(Optional.of(existsArticle));

        when(articleReportPersistencePort.existsByArticleIdAndUserId(saveCommand.getArticleId(), userId))
            .thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> articleReportService.saveArticleReport(saveCommand, userId))
            .isInstanceOf(ArticleReportDuplicationException.class);
    }

}

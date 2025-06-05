package article.domain.command;

import article.adapter.input.web.request.ArticleReportSaveRequest;
import article.adapter.output.persistence.enums.ReportReasonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArticleReportSaveCommand {

    private ReportReasonType reasonType;

    private String reason;

    private String articleId;

    public static ArticleReportSaveCommand of(ArticleReportSaveRequest articleReportSaveRequest) {
        return ArticleReportSaveCommand.builder()
            .reasonType(articleReportSaveRequest.getReasonType())
            .reason(articleReportSaveRequest.getReason())
            .articleId(articleReportSaveRequest.getArticleId())
            .build();
    }

}

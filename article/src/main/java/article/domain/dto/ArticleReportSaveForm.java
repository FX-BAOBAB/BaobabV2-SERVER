package article.domain.dto;

import article.adapter.output.persistence.enums.ReportReasonType;
import article.domain.command.ArticleReportSaveCommand;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleReportSaveForm {

    private ReportReasonType reasonType;

    private String reason;

    private LocalDateTime registeredAt;

    private String userId; // 신고자

    private String articleId;

    public static ArticleReportSaveForm of(ArticleReportSaveCommand command, String userId) {
        return ArticleReportSaveForm.builder()
            .reasonType(command.getReasonType())
            .reason(command.getReason())
            .registeredAt(LocalDateTime.now())
            .userId(userId)
            .articleId(command.getArticleId())
            .build();
    }

}

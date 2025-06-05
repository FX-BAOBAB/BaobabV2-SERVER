package article.adapter.output.persistence.repository;

import article.adapter.output.persistence.enums.ReportReasonType;
import article.domain.dto.ArticleReportSaveForm;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "article_report")
public class ArticleReport {

    @Id
    private String id;

    private ReportReasonType reasonType;

    private String reason;

    private LocalDateTime registeredAt;

    private String userId; // 신고자

    private String articleId;

    public static ArticleReport of(ArticleReportSaveForm articleReportSaveForm) {
        return ArticleReport.builder()
            .reasonType(articleReportSaveForm.getReasonType())
            .reason(articleReportSaveForm.getReason())
            .registeredAt(articleReportSaveForm.getRegisteredAt())
            .userId(articleReportSaveForm.getUserId())
            .articleId(articleReportSaveForm.getArticleId())
            .build();
    }

}

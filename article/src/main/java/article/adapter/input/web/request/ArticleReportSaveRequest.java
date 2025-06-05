package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ReportReasonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReportSaveRequest {

    private ReportReasonType reasonType;

    private String reason;

    private String articleId;

}

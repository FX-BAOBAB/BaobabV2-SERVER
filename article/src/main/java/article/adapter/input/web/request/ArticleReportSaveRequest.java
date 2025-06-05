package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ReportReasonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReportSaveRequest {

    @NotNull(message = "필수 입력 사항입니다.")
    private ReportReasonType reasonType;

    @NotBlank(message = "필수 입력 사항입니다.")
    @Size(max = 200, message = "최대 200자까지 입력 가능합니다.")
    private String reason;

    @NotBlank(message = "필수 입력 사항입니다.")
    private String articleId;

}

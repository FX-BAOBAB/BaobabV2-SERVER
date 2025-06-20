package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ArticleSaleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleSaleStatusUpdateRequest {

    @NotBlank(message = "필수 입력 사항입니다.")
    private String articleId;

    @NotNull(message = "필수 입력 사항입니다.")
    private ArticleSaleStatus status;

}

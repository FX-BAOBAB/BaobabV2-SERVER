package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ArticleSaleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaleStatusUpdateRequest {

    @NotBlank(message = "null 값 또는 공백이 포함될 수 없습니다.")
    private String articleId;

    @NotNull(message = "null 값일 수 없습니다.")
    private ArticleSaleStatus status;

}

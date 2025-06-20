package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ArticleCategory;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleSaveRequest {

    @NotBlank(message = "필수 입력 사항입니다.")
    @Size(max = 200, message = "최대 200자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "필수 입력 사항입니다.")
    @Size(max = 500, message = "최대 500자까지 입력 가능합니다.")
    private String content;

    @NotNull(message = "필수 선택 사항입니다.")
    private ArticleCategory category;

    @NotNull(message = "필수 입력 사항입니다.")
    private Integer price;

}

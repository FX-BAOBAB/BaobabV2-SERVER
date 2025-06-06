package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ArticleCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateRequest {

    private String id;

    @NotBlank(message = "제목을 입력하세요")
    @Size(max = 200, message = "제목은 200자 이내로 작성하세요")
    private String title;

    @NotBlank(message = "물품에 대한 설명을 입력하세요")
    @Size(max = 500, message = "물품에 대한 설명은 500자 이내로 작성하세요")
    private String content;

    @NotNull(message = "카테고리를 선택하세요")
    private ArticleCategory category;

    @NotNull(message = "가격을 입력하세요")
    private Integer price;

    private List<String> deleteImageIdList;

}

package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ArticleCategory;
import jakarta.validation.constraints.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaveRequest {

    @NotBlank(message = "제목을 입력하세요")
    @Size(max = 200)
    private String title;

    @NotBlank(message = "물품에 대한 설명을 입력하세요")
    @Size(max = 500)
    private String content;

    @NotNull(message = "카테고리를 선택하세요")
    private ArticleCategory category;

    @NotNull(message = "가격을 입력하세요")
    private Integer price;

    @NotNull(message = "물품에 대한 사진을 첨부하세요")
    private List<MultipartFile> imageList;

}

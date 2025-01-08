package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ArticleCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaveRequest {

    @Size(max = 200)
    private String title;

    @Size(max = 500)
    private String content;

    @NotNull
    private ArticleCategory category;

    private int price;

    @NotNull
    private List<MultipartFile> imageList;

}
